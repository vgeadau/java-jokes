package com.ness.jokes.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for {@link CustomExceptionHandler}.
 */
@ExtendWith(MockitoExtension.class)
public class CustomExceptionHandlerUnitTest {

    private static final String ERROR_MESSAGE = "Some error message";

    CustomExceptionHandler customExceptionHandler = new CustomExceptionHandler();

    @Test
    void handleException_IllegalArgumentException_should_succeed() {
        // given
        Exception exception = new IllegalArgumentException(ERROR_MESSAGE);

        // when
        ResponseEntity<String> result = customExceptionHandler.handleException(exception);

        // then
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    void handleException_IllegalStateException_should_succeed() {
        // given
        Exception exception = new IllegalStateException(ERROR_MESSAGE);

        // when
        ResponseEntity<String> result = customExceptionHandler.handleException(exception);

        // then
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }


    @Test
    void handleException_Other_should_succeed() {
        // given
        Exception exception = new IOException(ERROR_MESSAGE);

        // when
        ResponseEntity<String> result = customExceptionHandler.handleException(exception);

        // then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    void resolveException_should_succeed() {
        // given
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        Object handler = new Object();
        Exception ex = new Exception();

        // when
        ModelAndView result = customExceptionHandler.resolveException(request, response, handler, ex);

        // then
        assertNotNull(result);
    }
}
