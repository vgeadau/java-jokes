package com.ness.jokes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class responsible with persisting of JSON string in a REDIS DB.
 *
 */
@Service
public class PersistenceService {
    public static final String KEY = "LATEST_JOKES";

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public PersistenceService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * overrides last jokes provided by the API (if they exist).
     * @param jokes String JSON
     */
    public void saveJokes(String jokes) {
        if (Boolean.TRUE.equals(redisTemplate.hasKey(KEY))) {
            redisTemplate.delete(KEY);
        }
        redisTemplate.opsForList().rightPushAll(KEY, jokes);
    }

    /**
     * gets persisted jokes.
     * @return list of [(id, JokeJSON),(id, JokeJSON)] however since we always override entries, list always has 1 element.
     */
    public List<String> getJokes() {
        return redisTemplate.opsForList().range(KEY, 0, -1);
    }
}
