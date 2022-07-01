package com.lesonTask.practic.service.jsonService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lesonTask.practic.model.Cart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class JsonService {

    private final Logger LOGGER = LoggerFactory.getLogger(JsonService.class);

    @Autowired
    private ObjectMapper objectMapper;

    public void writeCartToJsonFile(Cart cart){
            try {
                objectMapper.writeValue(new File("Cart.json"), cart);
                LOGGER.info("File saved...");
            } catch (JsonProcessingException e) {
                LOGGER.debug("failed conversion: Cart object to Json", e);
            } catch (IOException e) {
                throw new RuntimeException("File IO error in writeCartToJsonFile()...", e);
            }
    }
    }
