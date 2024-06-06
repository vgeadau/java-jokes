package com.ness.jokes.controller;

import com.ness.jokes.choreographer.JokeChoreographer;
import com.ness.jokes.model.Joke;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test class for target class: {@link JokeController}.
 */
@ExtendWith(MockitoExtension.class)
public class JokeControllerUnitTest {
    @Mock
    private JokeChoreographer jokeChoreographer = Mockito.mock(JokeChoreographer.class);

    @InjectMocks
    public JokeController target;

    @Test
    void getJokesShouldSucceed() {
        // given
        int count = 2;
        List<Joke> jokes = buildJokes();
        when(jokeChoreographer.getJokes(count)).thenReturn(jokes);

        // when
        ResponseEntity<?> result = target.getJokes(count);

        // then
        verify(jokeChoreographer).getJokes(count);
        verifyNoMoreInteractions(jokeChoreographer);

        assertNotNull(result);
        assertTrue(result.getStatusCode().is2xxSuccessful());
    }


    /**
     * builds a list of jokes.
     * @return List of Joke objects.
     */
    private List<Joke> buildJokes() {
        return List.of(buildJoke(1), buildJoke(2));
    }

    /**
     * Builds a joke.
     * @param id int
     * @return a Joke object
     */
    private Joke buildJoke(int id) {
        return new Joke(id, "type" + id, "setup" + id, "punchline" + id);
    }
}
