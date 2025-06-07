package com.PACKAGE.TRADETOWN.ECOMM.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.PACKAGE.TRADETOWN.ECOMM.Entity.Buyer;
import com.PACKAGE.TRADETOWN.ECOMM.Entity.Cart;
import com.PACKAGE.TRADETOWN.ECOMM.Entity.Cartitems;
import com.PACKAGE.TRADETOWN.ECOMM.Entity.Product;
import com.PACKAGE.TRADETOWN.ECOMM.Repository.CartRepository;
import com.PACKAGE.TRADETOWN.ECOMM.Repository.CartitemsRepository;
import com.PACKAGE.TRADETOWN.ECOMM.Service.CartService;
import com.PACKAGE.TRADETOWN.ECOMM.Service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
public class CartController {

	@Autowired ProductService prodserv;
	@Autowired CartService cartserv;
	@Autowired CartRepository cartrepo;
	@Autowired CartitemsRepository crtr;
	@PostMapping("/cart/add")
	public ResponseEntity<?> addToCart(HttpSession session, @RequestParam Long productId) {
		
		Buyer buyer = (Buyer) session.getAttribute("loggedinuser");
		
	    if (buyer == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in.");
	    }
	   String  username=buyer.getBuyername();
	    Optional<Product> product = prodserv.findById(productId);
	    cartserv.addToCart(username, product);
	    
	    
	    return ResponseEntity.ok("Added to cart");
	}

	@DeleteMapping("/cart/delete/{itemId}")
	public ResponseEntity<?> deleteFromCart(@PathVariable Long itemId) {
	    cartserv.removeFromCart(itemId);
	    return ResponseEntity.ok("Deleted from cart");
	}
		@GetMapping("/cart")
	public ResponseEntity<?> getall(HttpSession session)
	{
		Buyer user = (Buyer) session.getAttribute("loggedinuser");
		
		if (user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
		}
				String username = user.getBuyername();
		List<Cart> carts = cartrepo.findByUsername(username);
		
		if (carts.isEmpty()) {
			return ResponseEntity.ok(List.of()); // Return empty list if cart doesn't exist
		}
		
		// Use the first cart found
		Cart cart = carts.get(0);
		return ResponseEntity.ok(cart.getItems());
	}
	
	@GetMapping("/cart/count")
	public ResponseEntity<?> getCartCount(HttpSession session) {
		Buyer user = (Buyer) session.getAttribute("loggedinuser");
		
		if (user == null) {
			return ResponseEntity.ok("0"); // Return 0 if user is not logged in
		}
		
		String username = user.getBuyername();
		List<Cart> carts = cartrepo.findByUsername(username);
		
		if (carts.isEmpty()) {
			return ResponseEntity.ok("0"); // Return 0 if cart doesn't exist
		}
		
		// Use the first cart found and get item count
		Cart cart = carts.get(0);
		int itemCount = cart.getItems().size();
		
		return ResponseEntity.ok(String.valueOf(itemCount));
	}
	    

}
