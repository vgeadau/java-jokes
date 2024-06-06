package com.ness.jokes.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ness.jokes.model.Joke;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link StringService}.
 *
 */

@ExtendWith(MockitoExtension.class)
public class StringServiceUnitTest {
    @Mock
    private ObjectMapper objectMapper = Mockito.mock(ObjectMapper.class);

    @InjectMocks
    private StringService target;

    @Test
    void getJSON_should_succeed() throws Exception {
        // given
        List<Joke> jokes = List.of(new Joke(3, "type3", "setup3", "punchline3"));
        String expectedJSON = "{}";

        when(objectMapper.writeValueAsString(jokes)).thenReturn(expectedJSON);

        // when
        String result = target.getJSON(jokes);

        // then
        verify(objectMapper).writeValueAsString(jokes);
        verifyNoMoreInteractions(objectMapper);

        assertEquals(expectedJSON, result);
    }

}
