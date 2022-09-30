package com.nordic;

import java.time.Duration;
import java.util.concurrent.Callable;

public class RestClient implements Callable<String> {
    @Override
    public String call() {
        var result = String.format("[%s,%s]", firstIOCall(), secondIOCall());
        System.out.println(result);
        return result;
    }

    private String firstIOCall() {
        try {
            return networkCall(2);
        } catch (Exception e) {
            return null;
        }
    }

    private String secondIOCall() {
        try {
            return networkCall(4);
        } catch (Exception e) {
            return null;
        }
    }

    private String networkCall(int seconds) throws Exception {

        Thread.sleep(Duration.ofSeconds(seconds));
        return "successful in " + seconds;
    }
}
