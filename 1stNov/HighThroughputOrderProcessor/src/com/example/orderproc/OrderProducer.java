package com.example.orderproc;

import java.util.Random;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Simulates incoming orders and pushes into a ConcurrentLinkedQueue<Order>.
 */
public class OrderProducer implements Runnable {
    private final Queue<Order> orderQueue;
    private final AtomicBoolean running;
    private final String[] productIds;
    private final Random rnd = new Random();
    private final int ratePerSecond; // approximate orders produced per second per producer

    public OrderProducer(Queue<Order> orderQueue, AtomicBoolean running, String[] productIds, int ratePerSecond) {
        this.orderQueue = orderQueue;
        this.running = running;
        this.productIds = productIds;
        this.ratePerSecond = ratePerSecond;
    }

    @Override
    public void run() {
        try {
            while (running.get()) {
                // produce ratePerSecond orders per second (simple throttling)
                for (int i = 0; i < ratePerSecond && running.get(); i++) {
                    String pid = productIds[rnd.nextInt(productIds.length)];
                    int qty = rnd.nextInt(3) + 1; // 1..3
                    double price = (rnd.nextInt(1000) + 100) / 100.0;
                    Order o = new Order(pid, qty, price);
                    orderQueue.add(o); // ConcurrentLinkedQueue is thread-safe for add()
                }
                Thread.sleep(1000); // produce in bursts per second
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}