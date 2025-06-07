document.addEventListener('DOMContentLoaded', () => {
  const params = new URLSearchParams(window.location.search); 
  const storename = params.get('storename');
  console.log("Extracted storename:", storename);

  // Initialize cart badge
  updateCartBadgeCount();

  // Show loading indicator
  const productsLoader = document.getElementById('productsLoader');
  const productContainer = document.getElementById('productContainer');

  if (!storename) {
    productsLoader.style.display = 'none';
    productContainer.style.display = 'block';
    const emptyTemplate = document.getElementById('emptyStateTemplate').content.cloneNode(true);
    const emptyState = emptyTemplate.querySelector('.empty-state');
    emptyState.querySelector('h3').textContent = 'Store Not Found';
    emptyState.querySelector('p').textContent = 'Please check the URL and try again, or browse our other available stores.';
    productContainer.innerHTML = '';
    productContainer.appendChild(emptyState);
    return;
  }

  document.getElementById('storeTitle').innerHTML = storename;

  fetch(`/products/getall/${storename}`)
    .then(res => res.json())
    .then(products => {
      // Hide loader
      productsLoader.style.display = 'none';
      productContainer.style.display = 'grid';
      
      if (products.length === 0) {
        const emptyTemplate = document.getElementById('emptyStateTemplate').content.cloneNode(true);
        productContainer.innerHTML = '';
        productContainer.appendChild(emptyTemplate);
        return;
      }

      // Generate random categories for demo
      const categories = ['Electronics', 'Clothing', 'Home', 'Beauty', 'Books', 'Sports'];
      
      products.forEach((product, index) => {
        const card = document.createElement('div');
        card.className = 'product-card';
        card.dataset.price = product.productPrice; // For sorting
        card.setAttribute('data-aos', 'fade-up');
        card.setAttribute('data-aos-delay', (index % 5) * 100);
        
        // Random category for demo
        const randomCategory = categories[Math.floor(Math.random() * categories.length)];
        
        // Random discount (for some products)
        let originalPrice = '';
        let discount = '';
        if (Math.random() > 0.6) {
          const discountPercent = Math.floor(Math.random() * 30) + 10; // 10-40%
          const origPrice = Math.round(product.productPrice * 100 / (100 - discountPercent));
          originalPrice = `<span class="original-price">₹${origPrice}</span>`;
          discount = `<span class="discount-percentage">${discountPercent}% OFF</span>`;
        }
        
        // Show badge on some products
        const badges = ['NEW', 'BESTSELLER', 'HOT'];
        const showBadge = Math.random() > 0.7;
        const badgeHtml = showBadge ? 
          `<div class="product-badge">${badges[Math.floor(Math.random() * badges.length)]}</div>` : '';

        card.innerHTML = `
          <div class="product-image">
            <img src="/products/image/${product.id}" alt="${product.productName}">
            ${badgeHtml}
          </div>
          <div class="product-content">
            <div class="product-category">${randomCategory}</div>
            <div class="product-title">${product.productName}</div>
            <div class="product-price">
              <span class="current-price">₹${product.productPrice}</span>
              ${originalPrice}
              ${discount}
            </div>
            <div class="product-actions">
              <button class="btn cart-btn" onclick="addToCart(${product.id}, this)">
                <i class="fas fa-cart-plus"></i> Add to Cart
              </button>
              <button class="btn buy-btn" onclick="makePayment(${product.productPrice}, ${product.id}, '${product.productName.replace("'", "\\'")}')">
                <i class="fas fa-bolt"></i> Buy Now
              </button>
            </div>
          </div>
        `;

        productContainer.appendChild(card);
      });
    })
    .catch(err => {
      // Hide loader and show error
      productsLoader.style.display = 'none';
      productContainer.style.display = 'block';
      console.error(err);
      productContainer.innerHTML = `<p class="text-danger text-center">Failed to load products. Try again later.</p>`;
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
               // Redirect manually
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

function addToCart(productId, buttonElement) {
  // Show loading state on button
  if (buttonElement) {
    const buttonText = buttonElement.innerHTML;
    buttonElement.disabled = true;
    buttonElement.classList.add('cart-btn-loading');
    buttonElement.innerText = '';  // Hide text while loading
  }

  fetch(`/cart/add?productId=${productId}`, { 
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
    .then(res => {
      if (!res.ok) {
        if (res.status === 401) {
          throw new Error("You must be logged in to add items to cart");
        }
        throw new Error("Failed to add item to cart");
      }
      return res.text();
    })
    .then(msg => {
      // Restore button state
      if (buttonElement) {
        buttonElement.disabled = false;
        buttonElement.classList.remove('cart-btn-loading');
        buttonElement.innerHTML = '<i class="fas fa-check"></i> Added';
        buttonElement.style.backgroundColor = 'var(--success-color)';
        buttonElement.style.borderColor = 'var(--success-color)';
        buttonElement.style.color = 'white';
        
        // Reset button after 2 seconds
        setTimeout(() => {
          buttonElement.innerHTML = '<i class="fas fa-cart-plus"></i> Add to Cart';
          buttonElement.style.backgroundColor = '';
          buttonElement.style.borderColor = '';
          buttonElement.style.color = '';
        }, 2000);
      }
      
      // Update cart count badge
      updateCartBadgeCount();
      
      // Show toast notification instead of alert
      showNotification('Success', msg, 'success');
      
      // Optional: Refresh cart modal if it's open
      if (document.getElementById('cartModal').classList.contains('show')) {
        document.getElementById('cartModal').dispatchEvent(new Event('show.bs.modal'));
      }
    })
    .catch(err => {
      // Restore button state
      if (buttonElement) {
        buttonElement.disabled = false;
        buttonElement.classList.remove('cart-btn-loading');
        buttonElement.innerHTML = '<i class="fas fa-cart-plus"></i> Add to Cart';
      }
      
      // Show error notification instead of alert
      showNotification('Error', err.message || "Error adding to cart", 'danger');
    });
}


function deleteFromCart(itemId, element) {
  // Show loading state on button if provided
  if (element) {
    element.innerHTML = '<i class="fas fa-spinner fa-spin"></i>';
    element.disabled = true;
  }

  fetch(`/cart/delete/${itemId}`, { method: 'DELETE' })
    .then(res => res.text())
    .then(msg => {
      showNotification('Cart Updated', msg, 'success');
      document.getElementById('cartModal').dispatchEvent(new Event('show.bs.modal'));
      updateCartBadgeCount();
    })
    .catch(err => {
      showNotification('Error', err.message || "Failed to remove item", 'danger');
      if (element) {
        element.innerHTML = '<i class="fas fa-trash-alt"></i> Remove';
        element.disabled = false;
      }
    });
}

// Function to update cart badge count
function updateCartBadgeCount() {
  fetch('/cart/count')
    .then(res => res.text())
    .then(count => {
      const badge = document.getElementById('cartBadge');
      if (badge) {
        badge.textContent = count;
        badge.style.display = count === '0' ? 'none' : 'flex';
      }
    })
    .catch(err => console.error("Failed to get cart count", err));
}

// Toast notification function
function showNotification(title, message, type = 'info') {
  // Create toast container if it doesn't exist
  let toastContainer = document.querySelector('.toast-container');
  if (!toastContainer) {
    toastContainer = document.createElement('div');
    toastContainer.className = 'toast-container position-fixed bottom-0 end-0 p-3';
    toastContainer.style.zIndex = '1080';
    document.body.appendChild(toastContainer);
  }
  
  // Create toast element
  const toastId = 'toast-' + Date.now();
  const toast = document.createElement('div');
  toast.className = `toast align-items-center text-white bg-${type} border-0`;
  toast.id = toastId;
  toast.setAttribute('role', 'alert');
  toast.setAttribute('aria-live', 'assertive');
  toast.setAttribute('aria-atomic', 'true');
  toast.innerHTML = `
    <div class="d-flex">
      <div class="toast-body">
        <strong>${title}</strong>: ${message}
      </div>
      <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
    </div>
  `;
  
  toastContainer.appendChild(toast);
  
  // Show the toast
  const bsToast = new bootstrap.Toast(toast, { 
    autohide: true,
    delay: 3000
  });
  bsToast.show();
  
  // Remove toast from DOM after it's hidden
  toast.addEventListener('hidden.bs.toast', function() {
    toast.remove();
  });
}

const cartModal = document.getElementById('cartModal');
cartModal.addEventListener('show.bs.modal', function () {
  const container = document.getElementById('cartItemsContainer');
  
  // Show loading UI
  container.innerHTML = `
    <div class="text-center py-4">
      <div class="loader" style="width: 50px; height: 50px;">
        <div class="loader-inner"></div>
        <div class="loader-inner"></div>
        <div class="loader-inner"></div>
      </div>
      <p class="text-muted mt-3">Loading your cart...</p>
    </div>
  `;
  
  fetch('/cart')
    .then(res => {
      if (!res.ok) {
        throw new Error('Not logged in or server error');
      }
      return res.json();
    })
    .then(cartItems => {
      if (cartItems.length === 0) {
        container.innerHTML = `
          <div class="empty-state">
            <img src="https://cdn-icons-png.flaticon.com/512/4555/4555971.png" alt="Empty cart" style="max-width: 120px;">
            <h3>Your cart is empty</h3>
            <p>Looks like you haven't added any items to your cart yet.</p>
            <button class="btn btn-primary" data-bs-dismiss="modal">Continue Shopping</button>
          </div>
        `;
        return;
      }

      let html = '';
      let totalPrice = 0;
      
      cartItems.forEach(item => {
        totalPrice += parseFloat(item.price);
        html += `
          <div class="cart-item">
            <div class="cart-item-image">
              <img src="/products/image/${item.productid}" alt="${item.productName}">
            </div>
            <div class="cart-item-details">
              <div class="cart-item-title">${item.productName}</div>
              <div class="cart-item-price">₹${item.price}</div>
              <div class="cart-item-actions">
                <div class="quantity-control">
                  <button class="quantity-btn" onclick="updateQuantity(${item.id}, -1)">
                    <i class="fas fa-minus"></i>
                  </button>
                  <span class="cart-quantity">1</span>
                  <button class="quantity-btn" onclick="updateQuantity(${item.id}, 1)">
                    <i class="fas fa-plus"></i>
                  </button>
                </div>
                <span class="remove-item" onclick="deleteFromCart(${item.id}, this)">
                  <i class="fas fa-trash-alt"></i> Remove
                </span>
              </div>
            </div>
          </div>
        `;
      });
      
      // Add cart summary section
      html += `
        <div class="cart-summary">
          <div class="cart-total">
            <span class="cart-total-label">Total</span>
            <span class="cart-total-value">₹${totalPrice.toFixed(2)}</span>
          </div>
        </div>
      `;
      
      container.innerHTML = html;
    })
    .catch(err => {
      console.error("Error loading cart:", err);
      container.innerHTML = `
        <div class="empty-state">
          <img src="https://cdn-icons-png.flaticon.com/512/6798/6798218.png" alt="Login required" style="max-width: 120px;">
          <h3>Login Required</h3>
          <p>Please login to view and manage your shopping cart.</p>
          <a href="/buyer/loginpage" class="btn btn-primary">Go to Login</a>
        </div>
      `;
    });
});

// Function to update item quantity
function updateQuantity(itemId, change) {
  // This is a placeholder function - in a real implementation,
  // you would call the backend to update the quantity
  const quantityElement = event.target.closest('.quantity-control').querySelector('.cart-quantity');
  let currentQuantity = parseInt(quantityElement.textContent);
  
  // Update quantity (with min of 1)
  currentQuantity = Math.max(1, currentQuantity + change);
  quantityElement.textContent = currentQuantity;
  
  // In a real implementation, you would update the price based on quantity
  // For now, we'll just show a notification that this is a demo
  if (change !== 0) {
    showNotification('Demo Feature', 'Quantity update functionality is for demonstration only.', 'info');
  }
  
  // Example of what a real implementation might look like:
  /*
  fetch(`/cart/update/${itemId}?quantity=${currentQuantity}`, {
    method: 'PUT'
  })
    .then(res => res.json())
    .then(data => {
      // Update the UI with the new price
      const priceElement = event.target.closest('.cart-item').querySelector('.cart-item-price');
      priceElement.textContent = `₹${data.newPrice}`;
      
      // Update cart total
      document.querySelector('.cart-total-value').textContent = `₹${data.cartTotal}`;
      
      // Update badge
      updateCartBadgeCount();
    })
    .catch(err => {
      showNotification('Error', 'Failed to update quantity', 'danger');
    });
  */
}
