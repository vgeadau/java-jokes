package com.ness.jokes.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Joke DTO used by the API which this application calls.
 *
 */
@SuppressWarnings("unused")
public class Joke implements Serializable {
    private final int id;
    private final String type;
    private final String setup;
    private final String punchline;

    /**
     * all args constructor.
     *
     * @param id        int
     * @param type      String
     * @param setup     String
     * @param punchline String
     */
    public Joke(int id, String type, String setup, String punchline) {
        this.id = id;
        this.type = type;
        this.setup = setup;
        this.punchline = punchline;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getSetup() {
        return setup;
    }

    public String getPunchline() {
        return punchline;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(Joke.class);
    }

    @Override
    public boolean equals(Object o) {
        // Check if the objects are the same instance
        if (this == o) {
            return true;
        }

        // Check if the object is of the same class
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        // Cast the object to your class
        Joke joke = (Joke) o;

        // Compare the fields
        return id == joke.id &&
                Objects.equals(type, joke.type) &&
                Objects.equals(setup, joke.setup) &&
                Objects.equals(punchline, joke.punchline);
    }
}