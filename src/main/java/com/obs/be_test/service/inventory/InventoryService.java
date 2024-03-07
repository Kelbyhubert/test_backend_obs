package com.obs.be_test.service.inventory;

import java.util.List;

import org.springframework.data.domain.Page;

import com.obs.be_test.dto.request.InventoryReqDto;
import com.obs.be_test.dto.response.InventoryResDto;


public interface InventoryService {
    
    public List<InventoryResDto> getInventory(int itemId);
    public Page<InventoryResDto> getListPageInventory(int pageIndex, int size);
    public void saveInventory(InventoryReqDto newInventory);
    public void editInventory(int id, InventoryReqDto updatedInventory);
    public void deleteInventory(int id);
}
