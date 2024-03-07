package com.obs.be_test.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.obs.be_test.dto.request.ItemReqDto;


@SpringBootTest
@AutoConfigureMockMvc
public class ItemControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testCreateItemSuccess() throws JsonProcessingException, Exception {
        ItemReqDto request = new ItemReqDto();
        request.setName("Note");
        request.setPrice(200);

        mockMvc.perform(
            post("/api/v1/item/create")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(
            status().isCreated()
        );
    }

    @Test
    void testDeleteItemSuccess() throws JsonProcessingException, Exception {
        mockMvc.perform(
            delete("/api/v1/item/2")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            status().isOk()
        );
    }

    @Test
    void testUpdateItemSuccess() throws JsonProcessingException, Exception {
        ItemReqDto request = new ItemReqDto();
        request.setName("Note s");
        request.setPrice(200);

        mockMvc.perform(
            put("/api/v1/item/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(
            status().isOk()
        );
    }

    @Test
    void testCreateItemFailed() throws JsonProcessingException, Exception {
        ItemReqDto request = new ItemReqDto();
        request.setName("Note");
        request.setPrice(200);

        mockMvc.perform(
            post("/api/v1/item/create")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(
            status().isCreated()
        );
    }

    @Test
    void testDeleteItemFailed() throws JsonProcessingException, Exception {
        mockMvc.perform(
            delete("/api/v1/item/x")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            status().isBadRequest()
        );
    }

    @Test
    void testUpdateItemFailed() throws JsonProcessingException, Exception {
        ItemReqDto request = new ItemReqDto();
        request.setName("Note s");

        mockMvc.perform(
            put("/api/v1/item/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(
            status().isBadRequest()
        );
    }

    @Test
    void testDeleteItemNotFound() throws JsonProcessingException, Exception {
        mockMvc.perform(
            delete("/api/v1/item/100")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            status().isNotFound()
        );
    }

    @Test
    void testUpdateItemNotFound() throws JsonProcessingException, Exception {
        ItemReqDto request = new ItemReqDto();
        request.setName("Note s");
        request.setPrice(200);

        mockMvc.perform(
            put("/api/v1/item/100")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(
            status().isNotFound()
        );
    }


}
