package com.ness.jokes.choreographer;

import com.ness.jokes.model.Joke;
import com.ness.jokes.service.JokeService;
import com.ness.jokes.service.ValidatorService;
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
    private final ValidatorService validatorService;

    @Autowired
    public JokeChoreographer(JokeService jokeService, ValidatorService validatorService) {
        this.jokeService = jokeService;
        this.validatorService = validatorService;
    }

    /**
     * getJokes method.
     * @param count integer
     * @return list of jokes
     */
    public List<Joke> getJokes(int count) {
        String errorMessage = validatorService.getErrorMessage(count);
        if (!errorMessage.isEmpty()) {
            throw new IllegalArgumentException(errorMessage);
        }

        return jokeService.getJokes(count);
    }
}
