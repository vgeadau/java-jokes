package com.ness.jokes.service;

import com.ness.jokes.model.Joke;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Service responsible with fetching the jokes.
 */
@Service
public class JokeFetchService {
    private static final String JOKE_API_URL = "https://official-joke-api.appspot.com/random_joke";
    private static final int MAX_THREADS = 10;

    private RestTemplate restTemplate = new RestTemplate();
    private final ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREADS);

    /**
     * Method that splits count into batches of 10, and performs get calls on JOKE_API_URL for each batch.
     * @param count number of jokes to be fetched
     * @return List of Joke objects.
     */
    public List<Joke> fetchJokes(int count) {
        List<Joke> jokes = new ArrayList<>();
        int batchSize = MAX_THREADS;
        int fullBatches = count / batchSize;
        int remaining = count % batchSize;

        for (int i = 0; i < fullBatches; i++) {
            jokes.addAll(fetchJokesBatch(batchSize));
        }

        if (remaining > 0) {
            jokes.addAll(fetchJokesBatch(remaining));
        }

        return jokes;
    }

    /**
     * Method that performs calls for a batch (maximum 10).
     * @param batchSize int
     * @return List of Joke objects.
     */
    private List<Joke> fetchJokesBatch(int batchSize) {
        List<CompletableFuture<Joke>> futures = IntStream.range(0, batchSize)
                .mapToObj(i -> CompletableFuture.supplyAsync(() -> restTemplate.getForObject(JOKE_API_URL, Joke.class), executorService))
                .toList();

        return futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
