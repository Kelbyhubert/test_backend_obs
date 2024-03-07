package com.obs.be_test.controller;

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

import com.obs.be_test.dto.request.OrderReqDto;
import com.obs.be_test.dto.response.ApiResponse;
import com.obs.be_test.dto.response.ItemResDto;
import com.obs.be_test.dto.response.OrderResDto;
import com.obs.be_test.exception.InvalidBodyParamException;
import com.obs.be_test.service.order.OrderService;

@RestController
@RequestMapping(path = "/api/v1/order")
public class OrderController {
    
    OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResDto>> getorderById(@PathVariable int id ) {
        OrderResDto data = orderService.getOrder(id);
        ApiResponse<OrderResDto> apiResponse = new ApiResponse<>(HttpStatus.OK.value(), data, null);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<Page<OrderResDto>>> getListorderPage(@RequestParam int pageIndex ,@RequestParam int size) {
        Page<OrderResDto> data = orderService.getListOrder(pageIndex, size);
        ApiResponse<Page<OrderResDto>> apiResponse = new ApiResponse<>(HttpStatus.OK.value(), data, null);
        return ResponseEntity.ok(apiResponse);
    }


    @PostMapping("/create")
    public ResponseEntity<ApiResponse<String>> createorder(@RequestBody OrderReqDto neworder) {
        if(neworder.getItemId() == null){
            throw new InvalidBodyParamException("Invalid body parameter {itemId}");
        }

        if(neworder.getQty() == null){
            throw new InvalidBodyParamException("Invalid body parameter {qty}");
        }
        orderService.saveOrder(neworder);
        ApiResponse<String> apiResponse = new ApiResponse<>(HttpStatus.CREATED.value(), null, "Order Created");
        return new ResponseEntity<>(apiResponse,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> updateorder(@PathVariable int id ,@RequestBody OrderReqDto updatedorder) {
        if(updatedorder.getItemId() == null ){
            throw new InvalidBodyParamException("Invalid body parameter {itemId}");
        }

        if(updatedorder.getQty() == null){
            throw new InvalidBodyParamException("Invalid body parameter {qty}");
        }
        orderService.editOrder(id, updatedorder);
        ApiResponse<String> apiResponse = new ApiResponse<>(HttpStatus.CREATED.value(), null, "Order Updated");
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteorder(@PathVariable int id) {
        orderService.deleteOrder(id);
        ApiResponse<String> apiResponse = new ApiResponse<>(HttpStatus.CREATED.value(), null, "Order Deleted");
        return ResponseEntity.ok(apiResponse);
    }
}
