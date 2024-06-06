package com.ness.jokes.service;

import com.ness.jokes.model.Joke;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JokeService {

    private final JokeFetchService jokeFetchService;
    private final StringService stringService;
    private final PersistenceService persistenceService;

    @Autowired
    public JokeService(JokeFetchService jokeFetchService, StringService stringService, PersistenceService persistenceService) {
        this.jokeFetchService = jokeFetchService;
        this.stringService = stringService;
        this.persistenceService = persistenceService;
    }

    /**
     * Method that reads JOKES from the provided API and then persists them.
     * @param count number of requested jokes
     * @return List of Joke
     */
    public List<Joke> getJokes(int count) {
        List<Joke> jokes = jokeFetchService.fetchJokes(count);
        String jokesJSON = stringService.getJSON(jokes);
        persistenceService.saveJokes(jokesJSON);
        return jokes;
    }

    /**
     * Method that returns the persisted jokes.
     * @return List of String
     */
    public List<String> getPersistedJokes() {
        return persistenceService.getJokes();
    }
}