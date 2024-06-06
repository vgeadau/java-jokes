package com.ness.jokes.service;

import com.ness.jokes.model.Joke;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test class for target class: {@link JokeFetchService}.
 */
@ExtendWith(MockitoExtension.class)
public class JokeFetchServiceUnitTest {
    @Mock
    RestTemplate restTemplate = Mockito.mock(RestTemplate.class);

    private JokeFetchService target;

    @BeforeEach
    public void setUp() {
        target = new JokeFetchService();
        target.setRestTemplate(restTemplate);
    }

    @Test
    void fetchJokes_WithValidCount_ShouldSucceed() {
        // given
        int count = 1;
        Joke joke = buildJoke();

        when(restTemplate.getForObject(ArgumentMatchers.anyString(), ArgumentMatchers.eq(Joke.class)))
                .thenReturn(joke);

        // when
        List<Joke> result = target.fetchJokes(count);

        // then
        verify(restTemplate).getForObject(ArgumentMatchers.anyString(), ArgumentMatchers.eq(Joke.class));
        verifyNoMoreInteractions(restTemplate);

        assertEquals(count, result.size());
    }

    /**
     * Builds a joke.
     * @return a Joke object
     */
    private Joke buildJoke() {
        return new Joke(1, "type1", "setup1", "punchline1");
    }

}
