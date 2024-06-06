package com.ness.jokes.controller;

import com.ness.jokes.choreographer.JokeChoreographer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * JokeController API.
 *
 */
@RestController
@RequestMapping("/jokes")
@SuppressWarnings("unused")
public class JokeController {

    private final JokeChoreographer jokeChoreographer;

    @Autowired
    public JokeController(JokeChoreographer jokeChoreographer) {
        this.jokeChoreographer = jokeChoreographer;
    }

    /**
     * get jokes API.
     * @param count number of jokes by default 5.
     * @return HttpStatus 200 if OK, HttpStatus 400 if invalid count value was provided
     */
    @GetMapping
    public ResponseEntity<?> getJokes(@RequestParam(defaultValue = "5") int count) {
        return ResponseEntity.ok(jokeChoreographer.getJokes(count));
    }
}