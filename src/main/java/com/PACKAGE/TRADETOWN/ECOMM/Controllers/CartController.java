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
	public List<Cartitems>getall(HttpSession session)
	{
		Buyer user=(Buyer) session.getAttribute("loggedinuser");
		Long id=user.getBuyerid();
		return crtr.findByCartId((long) 1);
		
	}
	
	    

}
