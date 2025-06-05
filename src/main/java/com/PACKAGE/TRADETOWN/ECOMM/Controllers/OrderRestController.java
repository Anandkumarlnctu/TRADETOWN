package com.PACKAGE.TRADETOWN.ECOMM.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
