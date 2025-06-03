package com.PACKAGE.TRADETOWN.ECOMM.Controllers;

import org.springframework.http.MediaType;  // ✅ Correct

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.PACKAGE.TRADETOWN.ECOMM.Entity.Seller;
import com.PACKAGE.TRADETOWN.ECOMM.Repository.SellerRepository;

import com.PACKAGE.TRADETOWN.ECOMM.Service.StoreService;

@RestController
public class StoreControllers {
	
	@Autowired StoreService stser;
	@Autowired SellerRepository sellrepo;

	@GetMapping("/stores/getall")
	public List<Seller> getallseller()
	{
		return stser.getall(); 
	}
	
	@GetMapping("/seller/image/{id}")
	public ResponseEntity<byte[]> getImage(@PathVariable long id) {
	    java.util.Optional<Seller> optionalSeller = sellrepo.findById( id);

	    if (optionalSeller.isPresent()) {
	        Seller seller = optionalSeller.get();
	        byte[] imageBytes = seller.getStoreimage(); // assuming this is byte[]
	        String contentType = seller.getImagetype(); // assuming this is image/jpeg, etc.

	        return ResponseEntity
	                .ok()
	                .contentType(MediaType.parseMediaType(contentType))
	                .body(imageBytes);
	    }

	    return ResponseEntity.notFound().build();
	}

}
