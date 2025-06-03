package com.PACKAGE.TRADETOWN.ECOMM.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PACKAGE.TRADETOWN.ECOMM.Entity.Product;
import com.PACKAGE.TRADETOWN.ECOMM.Entity.Seller;
import com.PACKAGE.TRADETOWN.ECOMM.Repository.ProductRepository;
import com.PACKAGE.TRADETOWN.ECOMM.Repository.SellerRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductService {

	@Autowired ProductRepository prodrepo;
	@Autowired SellerRepository sellrepo;
	@Transactional
	public void addproduct(Product product, Long sellerid) {
		Seller user=sellrepo.findBySellerid(sellerid);
		
		
		product.setSeller(user);
		
		prodrepo.save(product);
		
	}
	public List<Product> gettallproductsbystorename(String storename) {
		
		Seller seller=sellrepo.findByStorename(storename);
		return prodrepo.findBySeller_Sellerid(seller.getSellerid());
		
	}


}
