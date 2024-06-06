package com.ness.jokes.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for {@link RedisConfiguration}.
 *
 */
@ExtendWith(MockitoExtension.class)
public class RedisConfigurationUnitTest {
    RedisConfiguration target = new RedisConfiguration();

    @Test
    void redisConnectionFactoryShouldCreateAnLettuceConnectionFactory() {
        RedisConnectionFactory result = target.redisConnectionFactory();
        assertTrue(result instanceof LettuceConnectionFactory);
    }

    @Test
    void redisTemplateShouldCreateATemplate() {
        RedisTemplate<String, Object> result = target.redisTemplate();
        assertNotNull(result);
    }
}

