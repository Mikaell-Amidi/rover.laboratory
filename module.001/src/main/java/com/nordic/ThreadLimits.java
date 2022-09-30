package com.nordic;

public class ThreadLimits {

    public static void main(String[] args) {
        System.out.println("Starting loom");
        for (int i = 0; i < 100000; i++)
            new Thread(ThreadLimits::handlingUserThreads).start();
        System.out.println("Exiting loom");
    }

    public static void handlingUserThreads() {
        System.out.println("User thread-" + Thread.currentThread().getName());
    }

}
