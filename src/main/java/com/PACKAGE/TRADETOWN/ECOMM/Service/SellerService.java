package com.PACKAGE.TRADETOWN.ECOMM.Service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.PACKAGE.TRADETOWN.ECOMM.Entity.Seller;
import com.PACKAGE.TRADETOWN.ECOMM.Repository.SellerRepository;

@Service
public class SellerService {

	@Autowired
	SellerRepository sellrepo;
	public Seller login(String sellername, String password) {
		Seller user=sellrepo.findBySellernameAndSellerpassword(sellername, password);
		if(user!=null) {
			return user;
		}
		
		return null;
	}
	
	 public void registerSeller(Seller seller) {
	       

	        sellrepo.save(seller);
	    }

	
}
