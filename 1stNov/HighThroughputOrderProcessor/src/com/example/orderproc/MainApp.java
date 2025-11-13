package com.example.orderproc;

import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;


public class MainApp {
    public static void main(String[] args) throws Exception {
        // Config
        final int NUM_PROCESSORS = 5;
        final int NUM_PRODUCERS = 2;
        final int PRODUCER_RATE_PER_SEC = 2000; // per producer -> total ~4000 orders/sec (simulate)
        final String OUT_FILE = "processed_orders.csv";

        // Shared structures
        Queue<Order> orderQueue = new ConcurrentLinkedQueue<>(); // incoming orders
        BlockingQueue<Order> writerQueue = new LinkedBlockingQueue<>(); // processed orders to write
        Inventory inventory = new Inventory();
        AtomicBoolean running = new AtomicBoolean(true);

        // Initialize inventory for a few products
        String[] products = { "PEN", "BOOK", "MOUSE", "PHONE", "LAPTOP" };
        for (String p : products) inventory.setStock(p, 10000); // plenty of stock

        // Start writer thread
        Thread writerThread = new Thread(new OrderWriter(writerQueue, running, OUT_FILE), "OrderWriter");
        writerThread.start();

        // Start producers
        ExecutorService producerPool = Executors.newFixedThreadPool(NUM_PRODUCERS);
        for (int i = 0; i < NUM_PRODUCERS; i++) {
            producerPool.submit(new OrderProducer(orderQueue, running, products, PRODUCER_RATE_PER_SEC));
        }

        // Start processors (workers)
        ExecutorService processors = Executors.newFixedThreadPool(NUM_PROCESSORS);
        for (int i = 0; i < NUM_PROCESSORS; i++) {
            processors.submit(new OrderProcessor(orderQueue, inventory, writerQueue, running));
        }

        // Let simulation run for (say) 15 seconds (for demo). In real system you'd run forever and shutdown hooks used.
        System.out.println("Simulation running... press Ctrl+C or wait for shutdown.");
        Thread.sleep(15_000);

        // Shutdown sequence
        System.out.println("Shutting down producers...");
        running.set(false);

        // Stop producers
        producerPool.shutdownNow();
        producerPool.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("Waiting for processors to finish remaining orders...");
        processors.shutdown();
        processors.awaitTermination(30, TimeUnit.SECONDS);

        System.out.println("Waiting for writer to flush...");
        writerThread.join(10_000);

        System.out.println("Done. Output file: " + OUT_FILE);
    }
}