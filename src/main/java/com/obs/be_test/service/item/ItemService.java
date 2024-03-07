package com.obs.be_test.service.item;

import org.springframework.data.domain.Page;

import com.obs.be_test.dto.request.ItemReqDto;
import com.obs.be_test.dto.response.ItemResDto;

public interface ItemService {
    
    public ItemResDto getItem(int id,boolean showStock);
    public Page<ItemResDto> getListItem(int pageIndex, int size, boolean showStock);
    public void saveItem(ItemReqDto newItem);
    public void editItem(int id, ItemReqDto updatedItem);
    public void deleteItem(int id);
    
}
