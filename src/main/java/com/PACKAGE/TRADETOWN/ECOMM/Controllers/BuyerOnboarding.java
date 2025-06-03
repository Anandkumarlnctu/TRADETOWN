package com.PACKAGE.TRADETOWN.ECOMM.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.PACKAGE.TRADETOWN.ECOMM.Entity.Buyer;
import com.PACKAGE.TRADETOWN.ECOMM.Service.BuyerService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class BuyerOnboarding {
	
	@Autowired BuyerService buyser;

	@GetMapping("/buyer/loginpage")
	public String buyerloginpage()
	{
		return "buyerlogin";
	}
	@GetMapping("/buyer/registerpage")
	public String buyerregisterpage()
	{
		return "buyerregister";
	}
	@GetMapping("/buyer/login")
	public String buyerloginendpoint(@ModelAttribute Buyer buyer,HttpServletRequest request)
	{
		Buyer user=buyser.login(buyer.getBuyername(),buyer.getBuyerpassword());
		if(user!=null)
		{
			HttpSession session = request.getSession(true);
			session.setAttribute("loggedinuser", user);
			return "redirect:/buyerlogin/success";
		}
		return "redirect:/buyer/loginpage";
		
	}
	@PostMapping("/buyer/register")
	public String buyerregisterendpoint(@ModelAttribute Buyer buyer)
	{
		buyser.register(buyer);
		return "redirect:/buyer/loginpage";
	}
	@GetMapping("/buyerlogin/success")
	public String buyerloginsuccesspage(HttpSession session)
	{
		Buyer user=(Buyer) session.getAttribute("loggedinuser");
		if(user!=null)
		{
			return "buyersuccesslogin";
		}
		return "redirect:/buyer/loginpage";
	}
	
	@GetMapping("/store")
	public String getstore(HttpSession session)
	{
		Buyer user=(Buyer) session.getAttribute("loggedinuser");
		if(user==null)
		{
			return "redirect:/buyer/loginpage";
		}
		return "stores";
	}

	
}
