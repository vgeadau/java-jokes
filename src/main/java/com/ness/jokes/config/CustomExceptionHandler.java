package com.ness.jokes.config;

import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * Class used for exception handling.
 *
 */
@ControllerAdvice
@RestController
public class CustomExceptionHandler implements HandlerExceptionResolver {

    /**
     * Method that handles IllegalArgumentException but can be used to handle other exceptions too.
     * @param ex Exception
     * @return ResponseEntity
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<String> handleException(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (ex instanceof IllegalArgumentException) {
            status = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof IllegalStateException) {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(ex.getMessage(), status);
    }

    @Override
    public ModelAndView resolveException(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            Object handler,
            @Nonnull Exception ex) {
        // Handle exceptions and customize responses here if needed.
        return new ModelAndView();
    }
}