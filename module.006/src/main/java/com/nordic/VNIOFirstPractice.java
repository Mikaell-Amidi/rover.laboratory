package com.nordic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.IntStream;

public class VNIOFirstPractice {
    public static void main(String[] args) {
        ThreadFactory factory = Thread.ofVirtual().name("user thread-", 0).factory();
        try (ExecutorService executor = Executors.newThreadPerTaskExecutor(factory)) {
            IntStream.range(0, 100).forEach(i -> {
                executor.submit(new RestClient());
            });
        }
    }
}