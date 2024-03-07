package com.obs.be_test.service.order;

import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.obs.be_test.dao.ItemDao;
import com.obs.be_test.dao.OrderDao;
import com.obs.be_test.dto.request.OrderReqDto;
import com.obs.be_test.dto.response.OrderResDto;
import com.obs.be_test.exception.InvalidBodyParamException;
import com.obs.be_test.exception.NotFoundException;
import com.obs.be_test.model.Item;
import com.obs.be_test.model.Order;

@Service
public class OrderServiceImpl implements OrderService {

    OrderDao orderDao;
    ItemDao itemDao;

    public OrderServiceImpl(OrderDao orderDao, ItemDao itemDao){
        this.orderDao = orderDao;
        this.itemDao = itemDao;
    } 

    @Override
    public OrderResDto getOrder(int id) {
        Order order = orderDao.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Order Not Found");
        });

        OrderResDto orderResDto = new OrderResDto();
        orderResDto.setId(order.getId());
        orderResDto.setItemId(order.getItem().getId());
        orderResDto.setName(order.getItem().getName());
        orderResDto.setQty(order.getQty());
        
        return orderResDto;
    }

    @Override
    public Page<OrderResDto> getListOrder(int pageIndex, int size) {
        Pageable pageable = PageRequest.of(pageIndex, size);
        return orderDao.findAll(pageable).map(new Function<Order,OrderResDto>(){

            @Override
            public OrderResDto apply(Order t) {

                OrderResDto orderResDto = new OrderResDto();
                orderResDto.setId(t.getId());
                orderResDto.setItemId(t.getItem().getId());
                orderResDto.setName(t.getItem().getName());
                orderResDto.setQty(t.getQty());
        
                return orderResDto;
            }

            
        });
        
    }

    @Override
    public void saveOrder(OrderReqDto newOrder) {
        Item item = itemDao.findById(newOrder.getItemId()).orElseThrow(() -> {
            throw new NotFoundException("Item Not Found");
        });

        if(item.getCurrentStock() < newOrder.getQty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Insufficient Stock");
        }

        Order order = new Order();
        order.setItem(item);
        order.setQty(newOrder.getQty());
        
        orderDao.save(order);
    }

    @Override
    public void editOrder(int id, OrderReqDto updatedOrder) {
        
        Order order = orderDao.findById(id).orElseThrow(() -> {
            throw new NotFoundException( "Order Not Found");
        });

        Item item = itemDao.findById(updatedOrder.getItemId()).orElseThrow(() -> {
            throw new NotFoundException("Item Not Found");
        });

        // stock need to be added with the order qty to see if the stock sufficient with the new order qty (if item same)
        if(order.getItem().getId() == item.getId()){
            if((item.getCurrentStock() + order.getQty()) < updatedOrder.getQty()){
                throw new InvalidBodyParamException("Insufficient Stock");
            }
        }else{
            if(item.getCurrentStock() < updatedOrder.getQty()){
                throw new InvalidBodyParamException("Insufficient Stock");
            }
        }


        order.setItem(item);
        order.setQty(updatedOrder.getQty());
        
        orderDao.save(order);

    }

    @Override
    public void deleteOrder(int id) {
        Order order = orderDao.findById(id).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order Not Found");
        });

        orderDao.deleteById(order.getId());

    }
    
}
