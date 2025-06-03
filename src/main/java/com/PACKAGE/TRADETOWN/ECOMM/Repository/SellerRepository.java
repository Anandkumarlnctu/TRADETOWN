package com.PACKAGE.TRADETOWN.ECOMM.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PACKAGE.TRADETOWN.ECOMM.Entity.Seller;

public interface SellerRepository extends JpaRepository<Seller,Long>{
	Seller findBySellernameAndSellerpassword(String sellername,String Sellerpassword);
	
	Seller findBySellerid(Long sellerid);
	Seller findByStorename(String storename);

}
