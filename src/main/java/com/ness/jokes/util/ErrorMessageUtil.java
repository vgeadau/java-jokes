package com.ness.jokes.util;

/**
 * Utility class that holds constants used throughout Joke application.
 */
public final class ErrorMessageUtil {

    public static final String INVALID_COUNT_ERROR = "You can get no more than 100 jokes at a time";

    /**
     * private constructor for utility class.
     */
    private ErrorMessageUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
}
