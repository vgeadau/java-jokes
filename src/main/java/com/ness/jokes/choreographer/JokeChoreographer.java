package com.ness.jokes.choreographer;

import com.ness.jokes.model.Joke;
import com.ness.jokes.service.JokeService;
import com.ness.jokes.util.ErrorMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * choreographer class responsible with calling the service classes.
 *
 */
@Component
public class JokeChoreographer {

    private final JokeService jokeService;

    @Autowired
    public JokeChoreographer(JokeService jokeService) {
        this.jokeService = jokeService;
    }

    /**
     * getJokes method.
     * @param count integer
     * @return list of jokes
     */
    public List<Joke> getJokes(int count) {
        if (count < 1 || count > 100) {
            throw new IllegalArgumentException(ErrorMessageUtil.INVALID_COUNT_ERROR);
        }

        return jokeService.getJokes(count);
    }
}
