package com.PACKAGE.TRADETOWN.ECOMM.Controllers;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.PACKAGE.TRADETOWN.ECOMM.Entity.Seller;
import com.PACKAGE.TRADETOWN.ECOMM.Repository.SellerRepository;
import com.PACKAGE.TRADETOWN.ECOMM.Service.SellerService;
import jakarta.servlet.http.HttpServletRequest;




@Controller
public class SellerOnboarding {

	@Autowired
	SellerService selser;
	@Autowired SellerRepository selrepo;
	
	@GetMapping("/seller/logout")
	public String sellerlogout(HttpSession session)
	{
		session.invalidate();
		return "redirect:/seller/loginpage";
	}
	
	@GetMapping("/sellerlogin/success")
	public String sellerloginsuccess(HttpSession session)
	{
		Seller user=(Seller) session.getAttribute("loggedinuser");
		if(user==null)
		{
			return "redirect:/seller/loginpage";
		}
		return "sellersuccesslogin";
		
	}
	@GetMapping("/seller/info")
	@ResponseBody
	public Map<String, String> getSellerInfo(HttpSession session) {
	    Seller seller = (Seller) session.getAttribute("loggedinuser");
	    if (seller == null) {
	        return Map.of("error", "not_logged_in");
	    }
	    return Map.of(
	        "username", seller.getSellername(),
	        "storename", seller.getStorename()
	    );
	}

	@PostMapping("/seller/login")
	public String sellerlogin(@RequestParam("sellername") String sellername,
	                          @RequestParam("sellerpassword") String password,
	                          HttpServletRequest request) {
	    Seller user = selser.login(sellername, password);
	    if (user != null) {
	        // Clone the user object without the image
	        Seller sessionUser = new Seller();
	        sessionUser.setSellername(user.getSellername());
	        sessionUser.setSellerpassword(user.getSellerpassword());
	        sessionUser.setStorename(user.getStorename());
	        sessionUser.setStoredesc(user.getStoredesc());
	        // Do not set image in session

	        HttpSession session = request.getSession(true);
	        session.setAttribute("loggedinuser", sessionUser);

	        return "redirect:/sellerlogin/success";
	    }
	    return "sellerloginpage";
	}

	@GetMapping("/seller/loginpage")
	public String sellerlogin()
	{
		return "sellerloginpage";
	}
	
	@PostMapping("/seller/register")
	public String registerSeller(
	        @RequestParam("sellername") String sellername,
	        @RequestParam("sellerpassword") String sellerpassword,
	        @RequestParam("storename") String storename,
	        @RequestParam("storedesc") String storedesc,
	        @RequestParam("storeimage") MultipartFile file) {

	    Seller seller = new Seller();
	    seller.setSellername(sellername);
	    seller.setSellerpassword(sellerpassword);
	    seller.setStorename(storename);
	    seller.setStoredesc(storedesc);

	    try {
	        if (file != null && !file.isEmpty()) {
	            seller.setStoreimage(file.getBytes());
	            seller.setImagetype(file.getContentType());
	        }
	    } catch (IOException e) {
	        throw new RuntimeException("Failed to process store image", e);
	    }

	    selser.registerSeller(seller);
	    return "redirect:/seller/loginpage";
	}

	@GetMapping("/seller/registerpage")
	public String sellerregister() {
		return "sellerregisterpage";
	}
	
}
