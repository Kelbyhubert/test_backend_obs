package com.obs.be_test.service.inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.obs.be_test.dao.InventoryDao;
import com.obs.be_test.dao.ItemDao;
import com.obs.be_test.dto.request.InventoryReqDto;
import com.obs.be_test.dto.response.InventoryResDto;
import com.obs.be_test.exception.InvalidBodyParamException;
import com.obs.be_test.exception.NotFoundException;
import com.obs.be_test.model.Inventory;
import com.obs.be_test.model.Item;

import jakarta.transaction.Transactional;

@Service
public class InventoryServiceImpl implements InventoryService {

    private InventoryDao inventoryDao;
    private ItemDao itemDao;


    public InventoryServiceImpl(InventoryDao inventoryDao ,ItemDao itemDao) {
        this.inventoryDao = inventoryDao;
        this.itemDao = itemDao;
    }

    @Override
    public List<InventoryResDto> getInventory(int itemId) {
        List<InventoryResDto> inventoryList = new ArrayList<>();
        List<Inventory> allInventoryById = inventoryDao.findAllByItemId(itemId);

        for (Inventory inv : allInventoryById) {
            InventoryResDto invDto = new InventoryResDto();

            invDto.setId(inv.getId());
            invDto.setItemId(inv.getItem().getId());
            invDto.setName(inv.getItem().getName());
            invDto.setQty(inv.getQty());
            invDto.setType(inv.getType());

            inventoryList.add(invDto);
        }

        return inventoryList;
    }

    @Override
    public Page<InventoryResDto> getListPageInventory(int pageIndex, int size) {
                Pageable pageable = PageRequest.of(pageIndex, size);
        return inventoryDao.findAll(pageable).map(new Function<Inventory,InventoryResDto>(){

            @Override
            public InventoryResDto apply(Inventory t) {

                InventoryResDto invDto = new InventoryResDto();
                invDto.setId(t.getId());
                invDto.setItemId(t.getItem().getId());
                invDto.setName(t.getItem().getName());
                invDto.setQty(t.getQty());
                invDto.setType(t.getType());
        

                return invDto;
            }

            
        });
    }

    @Override
    @Transactional
    public void saveInventory(InventoryReqDto newInventory) {
        Item item = itemDao.findById(newInventory.getItemId()).orElseThrow(() -> {
            throw new NotFoundException("Item Not Found");
        });

        if(newInventory.getType() == 'W'){
            if(item.getCurrentStock() < newInventory.getQty()){
                throw new InvalidBodyParamException("Insufficient Stock");
            }
        }

        Inventory inventory = new Inventory();
        inventory.setItem(item);
        inventory.setQty(newInventory.getQty());
        inventory.setType(newInventory.getType());

        inventoryDao.save(inventory);
        
    }

    @Override
    @Transactional
    public void editInventory(int id, InventoryReqDto updatedInventory) {

        Inventory inventory = inventoryDao.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Inventory Not Found");
        });

        Item item = itemDao.findById(updatedInventory.getItemId()).orElseThrow(() -> {
            throw new NotFoundException("Item Not Found");
        });

        if(updatedInventory.getType() == 'W'){
            if(inventory.getItem().getId() == item.getId()){

                if((item.getCurrentStock() + inventory.getQty()) < updatedInventory.getQty()){
                    throw new InvalidBodyParamException("Insufficient Stock");
                }
            }else{
                if(item.getCurrentStock() < updatedInventory.getQty()){
                    throw new InvalidBodyParamException("Insufficient Stock");
                }
            }
        }

        inventory.setItem(item);
        inventory.setQty(updatedInventory.getQty());
        inventory.setType(updatedInventory.getType());

        inventoryDao.save(inventory);

    }

    @Override
    @Transactional
    public void deleteInventory(int id) {

        Inventory inventory = inventoryDao.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Inventory Not Found");
        });

        inventoryDao.delete(inventory);
    }
    
}
