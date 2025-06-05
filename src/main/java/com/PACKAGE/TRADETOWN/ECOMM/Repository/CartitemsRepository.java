package com.PACKAGE.TRADETOWN.ECOMM.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PACKAGE.TRADETOWN.ECOMM.Entity.Cartitems;

public interface CartitemsRepository extends JpaRepository<Cartitems,Long> {

	List<Cartitems> findByCartId(Long cartId);
}
