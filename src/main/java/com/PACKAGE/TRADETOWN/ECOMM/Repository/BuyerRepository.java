package com.PACKAGE.TRADETOWN.ECOMM.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PACKAGE.TRADETOWN.ECOMM.Entity.Buyer;

public interface BuyerRepository extends JpaRepository<Buyer,Long> {
	
	Buyer findByBuyernameAndBuyerpassword(String username,String userpassword);

}
