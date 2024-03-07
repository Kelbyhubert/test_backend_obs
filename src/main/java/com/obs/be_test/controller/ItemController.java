package com.obs.be_test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.obs.be_test.dto.request.ItemReqDto;
import com.obs.be_test.dto.response.ApiResponse;
import com.obs.be_test.dto.response.InventoryResDto;
import com.obs.be_test.dto.response.ItemResDto;
import com.obs.be_test.exception.InvalidBodyParamException;
import com.obs.be_test.exception.MissingParameterException;
import com.obs.be_test.service.item.ItemService;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping(path = "/api/v1/item")
public class ItemController {
    
    ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ItemResDto>> getItemById(@PathVariable int id ,@RequestParam(required = false) Boolean showStock) {
        if(showStock == null){
            throw new MissingParameterException("Invalid parameter {showStock}");
        }
        ItemResDto data = itemService.getItem(id, showStock);
        ApiResponse<ItemResDto> apiResponse = new ApiResponse<>(HttpStatus.OK.value(), data, null);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<Page<ItemResDto>>> getListItemPage(@RequestParam int pageIndex ,@RequestParam int size,@RequestParam(required = false) Boolean showStock) {
        if(showStock == null){
            throw new MissingParameterException("Invalid parameter {showStock}");
        }
        Page<ItemResDto> data = itemService.getListItem(pageIndex, size, showStock);
        ApiResponse<Page<ItemResDto>> apiResponse = new ApiResponse<>(HttpStatus.OK.value(), data, null);
        return ResponseEntity.ok(apiResponse);
    }


    @PostMapping("/create")
    public ResponseEntity<ApiResponse<String>> createItem(@RequestBody ItemReqDto newItem) {
        if(newItem.getName() == null || newItem.getName().equals("")){
            throw new InvalidBodyParamException("Invalid body parameter {name}");
        }

        if(newItem.getPrice() == null){
            throw new InvalidBodyParamException("Invalid body parameter {price}");
        }

        itemService.saveItem(newItem);
        ApiResponse<String> apiResponse = new ApiResponse<>(HttpStatus.CREATED.value(), null, "Item Created");
        return new ResponseEntity<>(apiResponse,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> updateItem(@PathVariable int id ,@RequestBody ItemReqDto updatedItem) {
        if(updatedItem.getName() == null || updatedItem.getName().equals("")){
            throw new InvalidBodyParamException("Invalid body parameter {name}");
        }

        if(updatedItem.getPrice() == null){
            throw new InvalidBodyParamException("Invalid body parameter {name}");
        }

        itemService.editItem(id, updatedItem);
        ApiResponse<String> apiResponse = new ApiResponse<>(HttpStatus.CREATED.value(), null, "Item Updated");
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteItem(@PathVariable int id) {
        itemService.deleteItem(id);
        ApiResponse<String> apiResponse = new ApiResponse<>(HttpStatus.CREATED.value(), null, "Item Deleted");
        return ResponseEntity.ok(apiResponse);
    }
    
}
