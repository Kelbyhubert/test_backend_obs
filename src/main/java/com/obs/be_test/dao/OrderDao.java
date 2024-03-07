package com.obs.be_test.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.obs.be_test.model.Order;

public interface OrderDao extends JpaRepository<Order,Integer> {
    
}
