package com.tenaciousd.jmsdemo;

/**
 * Consumer main.
 */
public class Main {
    public static void main(String[] args) {
        thread(new P2PMessageReceiver());
    }

    public static void thread(Runnable runnable) {
        Thread brokerThread = new Thread(runnable);
        brokerThread.start();
    }
}
