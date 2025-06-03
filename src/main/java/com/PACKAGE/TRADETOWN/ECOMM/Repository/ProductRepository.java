package com.PACKAGE.TRADETOWN.ECOMM.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PACKAGE.TRADETOWN.ECOMM.Entity.Product;

public interface ProductRepository extends JpaRepository<Product,Long>{
	
	

	List<Product> findBySeller_Sellerid(Long sellerid);


}
