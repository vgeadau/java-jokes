package com.ness.jokes.service;

import com.ness.jokes.util.ErrorMessageUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Validator Service class.
 */
@Service
public class ValidatorService {

    private final static Long FIFTEEN_MINUTES_MILLISECONDS = 1000L*60L*15L;

    /**
     * For single threaded apps. is sufficient HashMap, for multithreading ConcurrentHashMap is a better choice.
     */
    private Map<Long, Integer> mapAPICalls = new HashMap<>();

    public String getErrorMessage(int count) {
        String result = ErrorMessageUtil.NO_ERROR;
        if (count < 1 || count > 100) {
            result = ErrorMessageUtil.INVALID_COUNT_ERROR;
        } else {
            long timeOfCall = System.currentTimeMillis();

            long timeOfPreviousCalls = timeOfCall - FIFTEEN_MINUTES_MILLISECONDS;
            int numberOfCountsInTheLast15Minutes = computeCounts(timeOfPreviousCalls);

            if (numberOfCountsInTheLast15Minutes == 100) {
                result = ErrorMessageUtil.REQUEST_LIMIT_ALREADY_REACHED_ERROR;
            } else if (numberOfCountsInTheLast15Minutes + count > 100) {
                int requestsAllowed = numberOfCountsInTheLast15Minutes + count - 100;
                result = ErrorMessageUtil.REQUEST_LIMIT_ERROR + requestsAllowed;
            }
            mapAPICalls.put(timeOfCall, count);
        }
        return result;
    }

    private int computeCounts(long timeOfPreviousCalls) {
        int lastCounts = 0;

        // compute total counts in the last 15 minutes
        List<Long> keysToRemove = new ArrayList<>();
        for (Long key: mapAPICalls.keySet()) {
            if (key < timeOfPreviousCalls) {
                keysToRemove.add(key);
            } else {
                lastCounts += mapAPICalls.get(key);
            }
        }

        // remove expired counts (older than 15 minutes)
        for (Long keyToRemove : keysToRemove) {
            mapAPICalls.remove(keyToRemove);
        }

        return lastCounts;
    }

    public void setMapAPICalls(Map<Long, Integer> mapAPICalls) {
        this.mapAPICalls = mapAPICalls;
    }
}
