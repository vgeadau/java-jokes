package com.ness.jokes.service;

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
 * Unit tests for {@link JokeService}.
 *
 */
@ExtendWith(MockitoExtension.class)
public class JokeServiceUnitTest {
    @Mock
    private final JokeFetchService jokeFetchService = Mockito.mock(JokeFetchService.class);
    @Mock
    private final StringService stringService = Mockito.mock(StringService.class);
    @Mock
    private final PersistenceService persistenceService = Mockito.mock(PersistenceService.class);

    @InjectMocks
    private JokeService target;

    @Test
    void getJokes_should_succeed() {
        // given
        int count = 1;
        List<Joke> jokes = List.of(new Joke(1, "type", "setup", "punchline"));
        String jokesJSON = "[{\"id\":1,\"type\":\"type\",\"setup\":\"setup\",\"punchline\":\"punchline\"}]";

        when(jokeFetchService.fetchJokes(count)).thenReturn(jokes);
        when(stringService.getJSON(jokes)).thenReturn(jokesJSON);

        // when
        List<Joke> result = target.getJokes(count);

        // then
        verify(jokeFetchService).fetchJokes(count);
        verify(stringService).getJSON(jokes);
        verify(persistenceService).saveJokes(jokesJSON);
        verifyNoMoreInteractions(jokeFetchService);
        verifyNoMoreInteractions(stringService);
        verifyNoMoreInteractions(persistenceService);

        assertEquals(jokes, result);
    }

    @Test
    void getPersistedJokes_should_succeed() {
        // given
        String jokesJSON = "[{\"id\":1,\"type\":\"type\",\"setup\":\"setup\",\"punchline\":\"punchline\"}]";
        List<String> persistedJokes = List.of(jokesJSON);

        when(persistenceService.getJokes()).thenReturn(persistedJokes);

        // when
        List<String> result = target.getPersistedJokes();

        // then
        verify(persistenceService).getJokes();
        verifyNoMoreInteractions(persistenceService);
        verifyNoInteractions(stringService, jokeFetchService);

        assertEquals(persistedJokes, result);
    }
}
