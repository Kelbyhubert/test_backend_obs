package com.obs.be_test.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.obs.be_test.dto.request.InventoryReqDto;
import com.obs.be_test.dto.response.ApiResponse;
import com.obs.be_test.dto.response.InventoryResDto;
import com.obs.be_test.exception.InvalidBodyParamException;
import com.obs.be_test.service.inventory.InventoryService;

@RestController
@RequestMapping(path = "/api/v1/inventory")
public class InventoryController {
    
    InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<List<InventoryResDto>>> getInventoryById(@PathVariable int id ) {
        List<InventoryResDto> data = inventoryService.getInventory(id);
        ApiResponse<List<InventoryResDto>> apiResponse = new ApiResponse<>(HttpStatus.OK.value(), data, null);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<Page<InventoryResDto>>> getListInventoryPage(@RequestParam int pageIndex ,@RequestParam int size) {
        Page<InventoryResDto> data = inventoryService.getListPageInventory(pageIndex, size);
        ApiResponse<Page<InventoryResDto>> apiResponse = new ApiResponse<>(HttpStatus.OK.value(), data, null);
        return ResponseEntity.ok(apiResponse);
    }


    @PostMapping("/create")
    public ResponseEntity<ApiResponse<String>> createInventory(@RequestBody InventoryReqDto newInventory) {
        if(newInventory.getItemId() == null){
            throw new InvalidBodyParamException("Invalid body parameter {itemId}");
        }

        if(newInventory.getQty() == null){
            throw new InvalidBodyParamException("Invalid body parameter {qty}");
        }
        inventoryService.saveInventory(newInventory);
        ApiResponse<String> apiResponse = new ApiResponse<>(HttpStatus.CREATED.value(), null, "Inventory Created");
        return new ResponseEntity<>(apiResponse,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> updateInventory(@PathVariable int id ,@RequestBody InventoryReqDto updatedInventory) {
        if(updatedInventory.getItemId() == null){
            throw new InvalidBodyParamException("Invalid body parameter {itemId}");
        }

        if(updatedInventory.getQty() == null){
            throw new InvalidBodyParamException("Invalid body parameter {qty}");
        }
        inventoryService.editInventory(id, updatedInventory);
        ApiResponse<String> apiResponse = new ApiResponse<>(HttpStatus.OK.value(), null, "Inventory Updated");
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteInventory(@PathVariable int id) {
        inventoryService.deleteInventory(id);
        ApiResponse<String> apiResponse = new ApiResponse<>(HttpStatus.OK.value(), null, "Inventory Deleted");
        return ResponseEntity.ok(apiResponse);
    }
}
