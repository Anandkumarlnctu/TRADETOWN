package com.PACKAGE.TRADETOWN.ECOMM.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PACKAGE.TRADETOWN.ECOMM.Entity.Order;

public interface OrderRepository extends JpaRepository<Order,Long> {

}
