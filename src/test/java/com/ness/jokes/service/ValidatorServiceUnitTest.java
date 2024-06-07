package com.ness.jokes.service;

import com.ness.jokes.util.ErrorMessageUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ValidatorServiceUnitTest {

    private final ValidatorService target = new ValidatorService();

    @Test
    void getErrorMessage_withNoPreviousApiCalls_shouldSucceed() {
        // given
        HashMap<Long, Integer> mapApiCalls = new HashMap<>();
        target.setMapAPICalls(mapApiCalls);

        // when
        String result = target.getErrorMessage(5);

        // then
        assertTrue(result.isEmpty());
    }

    @Test
    void getErrorMessage_withWithPreviousApiCalls_shouldSucceed() {
        // given
        HashMap<Long, Integer> mapApiCalls = new HashMap<>();
        long prevCallKey = System.currentTimeMillis() - 1000;
        mapApiCalls.put(prevCallKey, 95);
        target.setMapAPICalls(mapApiCalls);

        // when
        String result = target.getErrorMessage(5);

        // then
        assertTrue(result.isEmpty());
    }

    @Test
    void getErrorMessage_withWithPreviousApiCallsAndSomeExpired_shouldSucceed() {
        // given
        HashMap<Long, Integer> mapApiCalls = new HashMap<>();
        long prevCallKey = System.currentTimeMillis();
        mapApiCalls.put(prevCallKey, 95);
        long expiredCallKey = prevCallKey - 1000L*60L*15L - 1L;
        mapApiCalls.put(expiredCallKey, 100);
        target.setMapAPICalls(mapApiCalls);

        // when
        String result = target.getErrorMessage(5);

        // then
        assertTrue(result.isEmpty());
    }

    @Test
    void getErrorMessage_withMaxCallsAlreadyReached_shouldFail() {
        // given
        HashMap<Long, Integer> mapApiCalls = new HashMap<>();
        long prevCallKey = System.currentTimeMillis() - 1000;
        mapApiCalls.put(prevCallKey, 100);
        target.setMapAPICalls(mapApiCalls);

        // when
        String result = target.getErrorMessage(5);

        // then
        assertEquals(ErrorMessageUtil.REQUEST_LIMIT_ALREADY_REACHED_ERROR, result);
    }

    @Test
    void getErrorMessage_withExceededLimit_shouldFail() {
        // given
        HashMap<Long, Integer> mapApiCalls = new HashMap<>();
        long prevCallKey = System.currentTimeMillis() - 1000;
        mapApiCalls.put(prevCallKey, 99);
        target.setMapAPICalls(mapApiCalls);

        // when
        String result = target.getErrorMessage(5);

        // then 99+5-100 = 4 so we are allowed to do only 4 calls
        assertEquals(ErrorMessageUtil.REQUEST_LIMIT_ERROR + 4, result);
    }

    @Test
    void getErrorMessage_withSuccessiveFails_shouldNotIncreaseAvailableCounts() {
        // given
        HashMap<Long, Integer> mapApiCalls = new HashMap<>();
        long prevCallKey = System.currentTimeMillis() - 1000;
        mapApiCalls.put(prevCallKey, 99);
        target.setMapAPICalls(mapApiCalls);

        // when
        target.getErrorMessage(5);
        String result = target.getErrorMessage(5);

        // then 99+5-100 = 4 so we are allowed to do only 4 calls
        assertEquals(ErrorMessageUtil.REQUEST_LIMIT_ERROR + 4, result);
    }

}
