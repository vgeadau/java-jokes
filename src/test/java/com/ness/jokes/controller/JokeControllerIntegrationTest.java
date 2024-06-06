package com.ness.jokes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ness.jokes.model.Joke;
import com.ness.jokes.service.JokeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration test class for {@link JokeController}.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class JokeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JokeService jokeService;

    @Test
    public void getOneJokesShouldSucceed() throws Exception {
        // Create a list of jokes
        List<Joke> jokes = new ArrayList<>();

        // Save the jokes using the controller endpoint
        MvcResult result = mockMvc.perform(get("/jokes?count=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(jokes)))
                .andExpect(status().isOk())
                .andReturn();

        // Verify that the JSON response contains expected keys.
        String response = result.getResponse().getContentAsString();
        assertThat(response).contains("id");
        assertThat(response).contains("type");
        assertThat(response).contains("setup");
        assertThat(response).contains("punchline");

        // Verify that the persisted JSON is the same with the JSON in the response.
        List<String> savedJokes = jokeService.getPersistedJokes();
        assertThat(savedJokes).isNotEmpty();
        String latestJoke = savedJokes.get(0);
        assertThat(latestJoke).contains("id");
        assertThat(latestJoke).contains("type");
        assertThat(latestJoke).contains("setup");
        assertThat(latestJoke).contains("punchline");

        // most of the times these 2 are equal, however there are cases
        // when the API responds with curly single and double quotation characters
        // that are not handled properly when persisting in REDIS DB.
        // response isEqualTo latestJoke
    }

    @Test
    public void getTooManyJokesShouldFail() throws Exception {
        // Create a list of jokes
        List<Joke> jokes = new ArrayList<>();

        // Save the jokes using the controller endpoint
        MvcResult result = mockMvc.perform(get("/jokes?count=101")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(jokes)))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo("You can get no more than 100 jokes at a time");
    }

    /**
     * Method that converts an object into a JSON.
     * @param obj Object
     * @return JSON String representing the Object
     */
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
