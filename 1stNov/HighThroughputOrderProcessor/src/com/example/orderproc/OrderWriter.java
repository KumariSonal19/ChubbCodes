package com.example.orderproc;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Single-threaded writer that takes processed orders and writes them to file.
 */
public class OrderWriter implements Runnable {
    private final BlockingQueue<Order> writerQueue;
    private final AtomicBoolean running;
    private final String outFilePath;

    public OrderWriter(BlockingQueue<Order> writerQueue, AtomicBoolean running, String outFilePath) {
        this.writerQueue = writerQueue;
        this.running = running;
        this.outFilePath = outFilePath;
    }

    @Override
    public void run() {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(outFilePath, true)))) {
            // header optionally
            pw.println("orderId,productId,quantity,price,createdAt,status,failureReason");
            pw.flush();

            while (running.get() || !writerQueue.isEmpty()) {
                Order o = writerQueue.poll();
                if (o == null) {
                    Thread.sleep(10);
                    continue;
                }
                pw.println(o.toString());
                pw.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}