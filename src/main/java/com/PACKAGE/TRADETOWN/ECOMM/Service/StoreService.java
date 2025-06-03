package com.PACKAGE.TRADETOWN.ECOMM.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.PACKAGE.TRADETOWN.ECOMM.Entity.Seller;
import com.PACKAGE.TRADETOWN.ECOMM.Repository.SellerRepository;

@Service
public class StoreService {

	@Autowired SellerRepository selrepo;
	
	public List<Seller> getall() {
		// TODO Auto-generated method stub
		return selrepo.findAll(); 
	}
	



}
