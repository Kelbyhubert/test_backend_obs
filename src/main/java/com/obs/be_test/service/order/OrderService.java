package com.obs.be_test.service.order;

import org.springframework.data.domain.Page;

import com.obs.be_test.dto.request.OrderReqDto;
import com.obs.be_test.dto.response.OrderResDto;

public interface OrderService {
    
    public OrderResDto getOrder(int id);
    public Page<OrderResDto> getListOrder(int pageIndex, int size);
    public void saveOrder(OrderReqDto newOrder);
    public void editOrder(int id, OrderReqDto updatedOrder);
    public void deleteOrder(int id);
}
