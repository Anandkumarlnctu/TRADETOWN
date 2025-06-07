package com.PACKAGE.TRADETOWN.ECOMM.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PACKAGE.TRADETOWN.ECOMM.Entity.Buyer;
import com.PACKAGE.TRADETOWN.ECOMM.Entity.Order;
import com.PACKAGE.TRADETOWN.ECOMM.Repository.OrderRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class OrderController {

	@Autowired
	private OrderRepository orderRepository;
	
	@GetMapping("/ordersuccess")
	public String ordersuccess()
	{
		
		return "ordersuccesshtml";
	}
	@GetMapping("/orderfail")
	public String orderfail()
	{
		return "orderfail";
	}
	
   
	
}
