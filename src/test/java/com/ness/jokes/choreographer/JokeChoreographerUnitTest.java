package com.ness.jokes.choreographer;

import com.ness.jokes.model.Joke;
import com.ness.jokes.service.JokeService;
import com.ness.jokes.util.ErrorMessageUtil;
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
 * Unit tests for {@link JokeChoreographer}.
 *
 */
@ExtendWith(MockitoExtension.class)
public class JokeChoreographerUnitTest {

    @Mock
    private JokeService jokeService = Mockito.mock(JokeService.class);

    @InjectMocks
    private JokeChoreographer target;

    @Test
    void getJokes_WithZeroCount_ShouldFail() {
        // given
        final int zeroCount = 0;

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> target.getJokes(zeroCount));
        String actualMessage = exception.getMessage();

        // then
        verifyNoInteractions(jokeService);

        assertEquals(ErrorMessageUtil.INVALID_COUNT_ERROR, actualMessage);
    }

    @Test
    void getJokes_WithCountTooBig_ShouldFail() {
        // given
        final int countTooBig = 101;

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> target.getJokes(countTooBig));
        String actualMessage = exception.getMessage();

        // then
        verifyNoInteractions(jokeService);

        assertEquals(ErrorMessageUtil.INVALID_COUNT_ERROR, actualMessage);
    }

    @Test
    void getJokes_WithValidCount_ShouldSucceed() {
        // given
        final int validCount = 1;
        List<Joke> jokes = List.of(buildJoke());

        when(jokeService.getJokes(validCount)).thenReturn(jokes);

        // when
        List<Joke> result = target.getJokes(validCount);

        // then
        verify(jokeService).getJokes(validCount);
        verifyNoMoreInteractions(jokeService);

        assertEquals(jokes, result);
    }

    /**
     * Builds a joke.
     * @return a Joke object
     */
    private Joke buildJoke() {
        return new Joke(1, "type1", "setup1", "punchline1");
    }
}
