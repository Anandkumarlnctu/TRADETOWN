package com.PACKAGE.TRADETOWN.ECOMM.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PACKAGE.TRADETOWN.ECOMM.Entity.Order;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByBuyername(String buyerName);
    List<Order> findByStorename(String storeName);
}
