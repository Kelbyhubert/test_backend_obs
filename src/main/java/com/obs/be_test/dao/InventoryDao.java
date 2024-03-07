package com.obs.be_test.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.obs.be_test.model.Inventory;

public interface InventoryDao extends JpaRepository<Inventory,Integer> {
    
    List<Inventory> findAllByItemId(int itemId);
}
