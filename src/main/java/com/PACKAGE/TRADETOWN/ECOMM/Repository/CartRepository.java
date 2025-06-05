package com.PACKAGE.TRADETOWN.ECOMM.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PACKAGE.TRADETOWN.ECOMM.Entity.Cart;
import com.PACKAGE.TRADETOWN.ECOMM.Entity.Cartitems;

public interface CartRepository extends JpaRepository<Cart,Long> {

	Cart findByUsername(String username);

	

}
