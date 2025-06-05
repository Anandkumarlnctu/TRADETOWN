package com.PACKAGE.TRADETOWN.ECOMM.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PACKAGE.TRADETOWN.ECOMM.Entity.Cart;
import com.PACKAGE.TRADETOWN.ECOMM.Entity.Cartitems;
import com.PACKAGE.TRADETOWN.ECOMM.Entity.Product;
import com.PACKAGE.TRADETOWN.ECOMM.Repository.CartRepository;
import com.PACKAGE.TRADETOWN.ECOMM.Repository.CartitemsRepository;

@Service
public class CartService {

	@Autowired CartRepository cartrepository;
	@Autowired CartitemsRepository cartitemsrepo;
	public void addToCart(String username, Optional<Product> product) {
	    if (product.isEmpty()) return;  // Optional safety check

	    Product prod = product.get();

	    // Check if cart already exists
	    Cart cart = cartrepository.findByUsername(username);
	    if (cart == null) {
	        cart = new Cart();
	        cart.setUsername(username);
	        cart = cartrepository.save(cart);  // save and reassign (for generated ID)
	    }

	    // Create new Cartitems and associate with cart
	    Cartitems item = new Cartitems();
	    item.setCart(cart);
	    item.setProductId(prod.getId());
	    item.setProductName(prod.getProductName());
	    item.setPrice(prod.getProductPrice());

	    // ✅ Add the item to the cart's list
	    cart.getItems().add(item);

	    // Save both cart and items
	    cartrepository.save(cart);  // Because of CascadeType.ALL, this saves items too
	}

	
	public void removeFromCart(Long cartItemId) {
	    cartitemsrepo.deleteById(cartItemId);
	}

	


}
