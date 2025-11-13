package com.example.orderproc;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Polls from a ConcurrentLinkedQueue, processes the order (payment + inventory),
 * and submits successful/failed orders to a writer queue.
 */
public class OrderProcessor implements Runnable {
    private final Queue<Order> orderQueue;
    private final Inventory inventory;
    private final BlockingQueue<Order> writerQueue;
    private final AtomicBoolean running;
    private final Random rnd = new Random();

    public OrderProcessor(Queue<Order> orderQueue, Inventory inventory, BlockingQueue<Order> writerQueue, AtomicBoolean running) {
        this.orderQueue = orderQueue;
        this.inventory = inventory;
        this.writerQueue = writerQueue;
        this.running = running;
    }

    @Override
    public void run() {
        try {
            while (running.get() || !orderQueue.isEmpty()) {
                Order order = orderQueue.poll(); // ConcurrentLinkedQueue.poll()
                if (order == null) {
                    // no work; avoid busy spin: sleep a little
                    Thread.sleep(5);
                    continue;
                }

                order.setStatus(Order.Status.PROCESSING);
                // 1) Reserve inventory
                boolean reserved = inventory.tryReserve(order.getProductId(), order.getQuantity());
                if (!reserved) {
                    order.setStatus(Order.Status.OUT_OF_STOCK);
                    order.setFailureReason("OUT_OF_STOCK");
                    writerQueue.put(order);
                    continue;
                }

                // 2) Simulate payment processing (random success/fail)
                boolean paymentOk = simulatePayment(order);
                if (!paymentOk) {
                    order.setStatus(Order.Status.FAILED);
                    order.setFailureReason("PAYMENT_FAILED");
                    // If payment failed, roll back stock (simple add back)
                    inventory.addStock(order.getProductId(), order.getQuantity());
                    writerQueue.put(order);
                    continue;
                }

                // 3) Completed
                order.setStatus(Order.Status.SUCCESS);
                writerQueue.put(order);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private boolean simulatePayment(Order order) {
        // Keep it simple: 98% success
        return rnd.nextInt(100) < 98;
    }
}