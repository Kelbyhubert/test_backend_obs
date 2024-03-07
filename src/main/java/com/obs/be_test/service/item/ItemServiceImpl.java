package com.obs.be_test.service.item;

import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.obs.be_test.dao.ItemDao;
import com.obs.be_test.dto.request.ItemReqDto;
import com.obs.be_test.dto.response.ItemResDto;
import com.obs.be_test.exception.NotFoundException;
import com.obs.be_test.model.Item;

import jakarta.transaction.Transactional;

@Service
public class ItemServiceImpl implements ItemService {

    private ItemDao itemDao;

    public ItemServiceImpl(ItemDao itemDao){
        this.itemDao = itemDao;
    }

    @Override
    public ItemResDto getItem(int id, boolean showStock) {
        Item item = itemDao.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Item Not Found");
        });

        ItemResDto itemResDto = new ItemResDto();
        itemResDto.setId(item.getId());
        itemResDto.setName(item.getName());
        itemResDto.setPrice(item.getPrice());

        if(showStock){
            itemResDto.setStock(item.getCurrentStock());
        }
        
        return itemResDto;
    }

    @Override
    public Page<ItemResDto> getListItem(int pageIndex, int size, boolean showStock) {

        Pageable pageable = PageRequest.of(pageIndex, size);
        return itemDao.findAll(pageable).map(new Function<Item,ItemResDto>(){

            @Override
            public ItemResDto apply(Item t) {

                ItemResDto itemResDto = new ItemResDto();
                itemResDto.setId(t.getId());
                itemResDto.setName(t.getName());
                itemResDto.setPrice(t.getPrice());
        
                if(showStock){
                    itemResDto.setStock(t.getCurrentStock());
                }

                return itemResDto;
            }

            
        });
    }

    @Override
    @Transactional
    public void saveItem(ItemReqDto newItem) {

        Item item = new Item();
        item.setName(newItem.getName());
        item.setPrice(newItem.getPrice());

        itemDao.save(item);
    }

    @Override
    @Transactional
    public void editItem(int id, ItemReqDto updatedItem) {
        Item item = itemDao.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Item Not Found");
        });
    
        item.setName(updatedItem.getName());
        item.setPrice(updatedItem.getPrice());

        itemDao.save(item);
    }

    @Override
    @Transactional
    public void deleteItem(int id) {
        Item item = itemDao.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Item Not Found");
        });

        itemDao.deleteById(item.getId());

    }
    
}
