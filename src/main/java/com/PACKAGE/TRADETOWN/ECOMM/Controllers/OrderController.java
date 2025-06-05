package com.PACKAGE.TRADETOWN.ECOMM.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class OrderController {

	@GetMapping("/ordersuccess")
	public String ordersuccess()
	{
		return "ordersuccess";
	}
	@GetMapping("/orderfail")
	public String orderfail()
	{
		return "orderfail";
	}
}
