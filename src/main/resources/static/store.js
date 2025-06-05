document.addEventListener('DOMContentLoaded', () => {
  const params = new URLSearchParams(window.location.search); 
  const storename = params.get('storename');
  console.log("Extracted storename:", storename);

  if (!storename) {
    document.getElementById('productContainer').innerHTML = `<p class="text-danger text-center">Invalid store.</p>`;
    return;
  }

  document.getElementById('storeTitle').innerHTML = `Welcome to ${storename}`;

  fetch(`/products/getall/${storename}`)
    .then(res => res.json())
    .then(products => {
      const container = document.getElementById('productContainer');
      if (products.length === 0) {
        container.innerHTML = `<p class="text-center text-muted">No products found for this store.</p>`;
        return;
      }

      products.forEach(product => {
        const card = document.createElement('div');
        card.className = 'product-card';

        card.innerHTML = `
          <img src="/products/image/${product.id}" alt="Product Image">
          <div class="product-content">
            <div class="product-title">${product.productName}</div>
            <div class="product-price">${product.productPrice}</div>
            <button class="cart-btn" onclick="addToCart(${product.id})">Add to Cart</button>
			<button class="buy-btn" onclick="makePayment(${product.productPrice}, ${product.productid}, '${product.productName.replace("'", "\\'")}')">Buy Now</button>

          </div>
        `;

        container.appendChild(card);
      });
    })
    .catch(err => {
      console.error(err);
      document.getElementById('productContainer').innerHTML = `<p class="text-danger text-center">Failed to load products. Try again later.</p>`;
    });
});

function makePayment(amount, productId, productName) {
  const finalAmount = amount * 100;
  const params = new URLSearchParams(window.location.search);
  const storename = params.get('storename');

  fetch('/create-order', {
    method: 'POST',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    body: `amount=${finalAmount}`
  })
    .then(response => response.json())
    .then(order => {
      const options = {
        key: "rzp_test_HX8no9EuxOuyNG",
        amount: order.amount,
        currency: "INR",
        name: "Your Company",
        description: `Payment for product ${productId}`,
        order_id: order.id,

        // Custom success handler
        handler: function (response) {
          fetch('/order/add', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
              storename: storename,
              ordername: productName
              // buyername will be fetched from the session on the backend
            })
          })
            .then(res => res.text())
            .then(msg => {
              alert("Order Placed: " + msg);
              window.location.href = "/success.html"; // Redirect manually
            })
            .catch(err => console.error("Order saving failed:", err));
        },

        prefill: {
          name: "User Name",
          email: "user@example.com"
        }
      };

      const rzp = new Razorpay(options);
      rzp.open();
    })
    .catch(error => {
      console.error("Payment initiation failed", error);
    });
}

function addToCart(productId) {
  fetch(`/cart/add?productId=${productId}&quantity=1`, { method: 'POST' })
    .then(res => res.text())
    .then(msg => alert(msg))
    .catch(err => alert("Error: " + err));
}


function deleteFromCart(itemId) {
  fetch(`/cart/delete/${itemId}`, { method: 'DELETE' })
    .then(res => res.text())
    .then(msg => {
      alert(msg);
      document.getElementById('cartModal').dispatchEvent(new Event('show.bs.modal'));
    })
    .catch(err => alert("Error: " + err));
}

const cartModal = document.getElementById('cartModal');
cartModal.addEventListener('show.bs.modal', function () {
  fetch('/cart')
    .then(res => res.json())
    .then(cartItems => {
      const container = document.getElementById('cartItemsContainer');
      if (cartItems.length === 0) {
        container.innerHTML = "<p>Your cart is empty.</p>";
        return;
      }

      let html = '<ul class="list-group">';
      cartItems.forEach(item => {
        html += `
          <li class="list-group-item d-flex justify-content-between align-items-center">
            <div>
              <strong>${item.product.name}</strong> (x${item.quantity})
              <br><small>₹${item.product.price} each</small>
            </div>
            <div class="text-end">
              <div>₹${item.product.price * item.quantity}</div>
              <button class="btn btn-sm btn-danger mt-1" onclick="deleteFromCart(${item.id})">Remove</button>
            </div>
          </li>`;
      });
      html += '</ul>';
      container.innerHTML = html;
    });
});
