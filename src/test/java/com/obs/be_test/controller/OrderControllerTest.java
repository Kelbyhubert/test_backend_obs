package com.obs.be_test.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.obs.be_test.dto.request.OrderReqDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {


    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testCreateorder() throws JsonProcessingException, Exception {
        OrderReqDto request = new OrderReqDto();
        request.setItemId(1);
        request.setQty(1);
        
        mockMvc.perform(
            post("/api/v1/order/create")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(
            status().isCreated()
        );
    }

    @Test
    void testCreateorderFailed() throws JsonProcessingException, Exception {
        OrderReqDto request = new OrderReqDto();
        request.setItemId(1);
        
        mockMvc.perform(
            post("/api/v1/order/create")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(
            status().isBadRequest()
        );
    }

    @Test
    void testCreateorderMoreThanStock() throws JsonProcessingException, Exception {
        OrderReqDto request = new OrderReqDto();
        request.setItemId(1);
        request.setQty(1000);
        
        mockMvc.perform(
            post("/api/v1/order/create")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(
            status().isBadRequest()
        );
    }

    @Test
    void testDeleteorder() throws JsonProcessingException, Exception {
        OrderReqDto request = new OrderReqDto();

        mockMvc.perform(
            delete("/api/v1/order/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(
            status().isOk()
        );
    }

    @Test
    void testDeleteorderNotFound() throws JsonProcessingException, Exception {
        OrderReqDto request = new OrderReqDto();

        mockMvc.perform(
            delete("/api/v1/order/1000")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(
            status().isNotFound()
        );
    }

    @Test
    void testUpdateorder() throws JsonProcessingException, Exception {
        OrderReqDto request = new OrderReqDto();
        request.setItemId(2);
        request.setQty(5);

        mockMvc.perform(
            put("/api/v1/order/5")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(
            status().isOk()
        );

    }

    @Test
    void testUpdateorderFailed() throws JsonProcessingException, Exception {
        OrderReqDto request = new OrderReqDto();
        request.setItemId(2);

        mockMvc.perform(
            put("/api/v1/order/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(
            status().isBadRequest()
        );
    }

    @Test
    void testUpdateorderMoreThanStock() throws JsonProcessingException, Exception {
        OrderReqDto request = new OrderReqDto();
        request.setItemId(2);
        request.setQty(10000);

        mockMvc.perform(
            put("/api/v1/order/5")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(
            status().isBadRequest()
        );
    }
}
