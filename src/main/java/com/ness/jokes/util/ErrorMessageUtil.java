package com.ness.jokes.util;

/**
 * Utility class that holds constants used throughout Joke application.
 */
public final class ErrorMessageUtil {

    public static final String NO_ERROR = "";
    public static final String INVALID_COUNT_ERROR = "You can get no more than 100 jokes at a time";
    public static final String REQUEST_LIMIT_ALREADY_REACHED_ERROR =
            "During the multiple requests you are allowed to obtain 100 jokes in the last 15 minutes interval";
    public static final String REQUEST_LIMIT_ERROR =
            "At this time you are only allowed to a number of request equal to = ";

    /**
     * private constructor for utility class.
     */
    private ErrorMessageUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
}
