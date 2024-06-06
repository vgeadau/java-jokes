package com.ness.jokes.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ness.jokes.model.Joke;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service handling string related operations.
 *
 */
@Service
public class StringService {
    private final ObjectMapper objectMapper;

    @Autowired
    public StringService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String getJSON(List<Joke> jokes) {
        try {
            return objectMapper.writeValueAsString(jokes);
        } catch (Exception e) {
            throw new IllegalStateException("Unable to covert object to JSON");
        }
    }
}
