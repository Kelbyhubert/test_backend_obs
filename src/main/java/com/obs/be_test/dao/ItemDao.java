package com.obs.be_test.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.obs.be_test.model.Item;

@Repository
public interface ItemDao extends JpaRepository<Item,Integer> {
    
}
