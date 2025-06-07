package com.PACKAGE.TRADETOWN.ECOMM.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.PACKAGE.TRADETOWN.ECOMM.Entity.Buyer;
import com.PACKAGE.TRADETOWN.ECOMM.Entity.Order;
import com.PACKAGE.TRADETOWN.ECOMM.Repository.OrderRepository;

import jakarta.servlet.http.HttpSession;

@RestController
public class OrderRestController {
	@Autowired OrderRepository orderrepo;
		@GetMapping("/orders/user")
    public ResponseEntity<?> getUserOrders(HttpSession session) {
        // Get the logged in user from the session
        Buyer buyer = (Buyer) session.getAttribute("loggedinuser");
        
        if (buyer == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }
        
        // Get the buyer's username
        String username = buyer.getBuyername();
        
        // Find all orders by this buyer
        List<Order> orders = orderrepo.findByBuyername(username);
        
        return ResponseEntity.ok(orders);
    }      @GetMapping("/orders/store")
    public ResponseEntity<?> getStoreOrders(HttpSession session) {
        // Get the seller info from the session
        Object sellerObj = session.getAttribute("loggedinuser");
        
        if (sellerObj == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Seller not logged in");
        }
        
        // Check if the object is a Seller instance
        try {
            // Cast to com.PACKAGE.TRADETOWN.ECOMM.Entity.Seller
            com.PACKAGE.TRADETOWN.ECOMM.Entity.Seller seller = (com.PACKAGE.TRADETOWN.ECOMM.Entity.Seller) sellerObj;
            
            // Get the store name from the seller object
            String storeName = seller.getStorename();
        
            if (storeName == null || storeName.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Store name not available");
            }
            
            // Find all orders for this store
            List<Order> orders = orderrepo.findByStorename(storeName);
            
            return ResponseEntity.ok(orders);
        } catch (ClassCastException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not a valid seller session");
        }
    }
	
	@PostMapping("/order/add")
	public ResponseEntity<String> addOrder(@RequestBody Order data, HttpSession session) {
	    Buyer buyername =  (Buyer) session.getAttribute("loggedinuser"); // or however you store it
	    String storename = data.getStorename();
	    String productName = data.getOrdername();

	    Order order = new Order();
	    order.setBuyername(buyername.getBuyername());
	    order.setStorename(storename);
	    order.setOrdername(productName);

	    orderrepo.save(order);

	    return ResponseEntity.ok("Order stored successfully");
	}


}
