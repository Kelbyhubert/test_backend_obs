package com.obs.be_test.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.obs.be_test.dto.request.InventoryReqDto;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;




@SpringBootTest
@AutoConfigureMockMvc
public class InventoryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testCreateInventoryTopUp() throws JsonProcessingException, Exception {
        InventoryReqDto request = new InventoryReqDto();
        request.setItemId(1);
        request.setQty(20);
        request.setType('T');

        mockMvc.perform(
            post("/api/v1/inventory/create")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(
            status().isCreated()
        );
    }

    @Test
    void testCreateInventoryWithdraw() throws JsonProcessingException, Exception {
        InventoryReqDto request = new InventoryReqDto();
        request.setItemId(1);
        request.setQty(1);
        request.setType('W');

        mockMvc.perform(
            post("/api/v1/inventory/create")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(
            status().isCreated()
        );
    }

    @Test
    void testCreateInventoryWithdrawMoreThanStock() throws JsonProcessingException, Exception {
        InventoryReqDto request = new InventoryReqDto();
        request.setItemId(1);
        request.setQty(100);
        request.setType('W');

        mockMvc.perform(
            post("/api/v1/inventory/create")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(
            status().isBadRequest()
        );
    }

    @Test
    void testDeleteInventory() throws JsonProcessingException, Exception {
        InventoryReqDto request = new InventoryReqDto();

        mockMvc.perform(
            delete("/api/v1/inventory/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(
            status().isOk()
        );
    }

    @Test
    void testDeleteInventoryNotFound() throws JsonProcessingException, Exception {
        InventoryReqDto request = new InventoryReqDto();

        mockMvc.perform(
            delete("/api/v1/inventory/100")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(
            status().isNotFound()
        );
    }

    @Test
    void testUpdateInventory() throws JsonProcessingException, Exception {
        InventoryReqDto request = new InventoryReqDto();
        request.setItemId(1);
        request.setQty(1);
        request.setType('W');

        mockMvc.perform(
            put("/api/v1/inventory/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(
            status().isOk()
        );
    }

    @Test
    void testUpdateInventoryNotFound() throws JsonProcessingException, Exception {
        InventoryReqDto request = new InventoryReqDto();
        request.setItemId(100);
        request.setQty(1);
        request.setType('W');

        mockMvc.perform(
            put("/api/v1/inventory/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(
            status().isNotFound()
        );
    }

    @Test
    void testUpdateInventoryWithDrawMoreThanStock() throws JsonProcessingException, Exception {
        InventoryReqDto request = new InventoryReqDto();
        request.setItemId(1);
        request.setQty(100000);
        request.setType('W');

        mockMvc.perform(
            put("/api/v1/inventory/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(
            status().isBadRequest()
        );
    }
}
