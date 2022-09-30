package com.nordic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceVirtualThread {
    public static void main(String[] args) {
        try(ExecutorService service = Executors.newVirtualThreadPerTaskExecutor()) {
            service.submit(ExecutorServiceVirtualThread::taskOne);
            service.submit(ExecutorServiceVirtualThread::taskTwo);
        }
    }

    public static void taskOne( ) {
        System.out.println("task one triggered");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("task two finished");
    }

    public static void taskTwo() {
        System.out.println("task two triggered");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("task two finished");
    }

}