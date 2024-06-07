package com.ness.jokes.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link PersistenceService}.
 *
 */
@ExtendWith(MockitoExtension.class)
@SuppressWarnings("unchecked")
public class PersistenceServiceUnitTest {

    @Mock
    private RedisTemplate<String, String> redisTemplate = Mockito.mock(RedisTemplate.class);

    @InjectMocks
    private PersistenceService persistenceService;

    @Test
    void getJokes_should_succeed() {
        // given
        List<String> list = List.of("Persisted JSON1", "Persisted JSON2");
        ListOperations<String, String> listOperations = Mockito.mock(ListOperations.class);

        when(redisTemplate.opsForList()).thenReturn(listOperations);
        when(listOperations.range(PersistenceService.KEY, 0, -1)).thenReturn(list);

        // when
        List<String> result = persistenceService.getJokes();

        // then
        verify(redisTemplate).opsForList();
        verifyNoMoreInteractions(redisTemplate);

        assertEquals(list, result);
    }

    @Test
    void saveLastJokes_nonExistentKey_shouldNotDelete() {
        // given
        String jokes = "Jokes JSON";
        ListOperations<String, String> listOperations = Mockito.mock(ListOperations.class);

        when(redisTemplate.hasKey(PersistenceService.KEY)).thenReturn(Boolean.FALSE);
        when(redisTemplate.opsForList()).thenReturn(listOperations);
        when(listOperations.rightPushAll(PersistenceService.KEY, jokes)).thenReturn(1L);

        // when
        persistenceService.saveLastJokes(jokes);

        // then
        verify(redisTemplate).hasKey(PersistenceService.KEY);
        verify(redisTemplate).opsForList();
        verifyNoMoreInteractions(redisTemplate);

        // as the tested method is void function we have no assert
    }

    @Test
    void saveLastJokes_withExistingKey_shouldDelete() {
        // given
        String jokes = "Jokes JSON";
        ListOperations<String, String> listOperations = Mockito.mock(ListOperations.class);

        when(redisTemplate.hasKey(PersistenceService.KEY)).thenReturn(Boolean.TRUE);
        when(redisTemplate.delete(PersistenceService.KEY)).thenReturn(Boolean.TRUE);
        when(redisTemplate.opsForList()).thenReturn(listOperations);
        when(listOperations.rightPushAll(PersistenceService.KEY, jokes)).thenReturn(1L);

        // when
        persistenceService.saveLastJokes(jokes);

        // then
        verify(redisTemplate).hasKey(PersistenceService.KEY);
        verify(redisTemplate).delete(PersistenceService.KEY);
        verify(redisTemplate).opsForList();
        verifyNoMoreInteractions(redisTemplate);

        // as the tested method is void function we have no assert
    }
}
