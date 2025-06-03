package com.PACKAGE.TRADETOWN.ECOMM.Controllers;




import java.io.IOException;
import java.net.URI;
import java.net.http.HttpHeaders;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.PACKAGE.TRADETOWN.ECOMM.Entity.Product;
import com.PACKAGE.TRADETOWN.ECOMM.Entity.Seller;
import com.PACKAGE.TRADETOWN.ECOMM.Repository.ProductRepository;
import com.PACKAGE.TRADETOWN.ECOMM.Repository.SellerRepository;
import com.PACKAGE.TRADETOWN.ECOMM.Service.ProductService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired ProductService prodserv;
	@Autowired SellerRepository sellrepo;
	@Autowired ProductRepository prodrepo;
	
	@PostMapping(value="/add",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> addproducts(@RequestParam("productname") String productname,
			@RequestParam("productprice") Double productprice,
			@RequestParam("productimage") MultipartFile productimage,HttpSession session)
	{
		
		Seller user=(Seller) session.getAttribute("loggedinuser");
		System.out.println("Session timeout (secs): " + session.getMaxInactiveInterval());
		 if (user == null) {
			 return ResponseEntity.status(HttpStatus.FOUND)
                     .location(URI.create("/seller/loginpage"))
                     .build();
		    }
		Seller seller=sellrepo.findBySellernameAndSellerpassword(user.getSellername(), user.getSellerpassword());
		Product prod=new Product();
		try {
			prod.setProductImage(productimage.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		prod.setProductName(productname);
		prod.setProductPrice(productprice);
		
		prodserv.addproduct(prod,seller.getSellerid());
		
		return ResponseEntity.status(HttpStatus.OK).body("products added");
	}
	
	@GetMapping("/getall/{storename}")
	public List<Product> getallproducts(@PathVariable String storename)
	{
		return prodserv.gettallproductsbystorename(storename);
	}
	
	
	@GetMapping("/image/{id}")
	public ResponseEntity<byte[]> getProductImage(@PathVariable Long id) {
	    Optional<Product> product = prodrepo.findById(id);
	    if (product == null || product.get() == null) {
	        return ResponseEntity.notFound().build();
	    }
	    Product prod=product.get();
	    byte[] image = prod.getProductImage();
	    
	    return ResponseEntity
                .ok()
               
                .body(image);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
