package com.example.orderproc;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Inventory {
    private final ConcurrentHashMap<String, AtomicInteger> stock = new ConcurrentHashMap<>();

    // initialize stock for a product
    public void setStock(String productId, int count) {
        stock.put(productId, new AtomicInteger(count));
    }

    // get current stock
    public int getStock(String productId) {
        AtomicInteger a = stock.get(productId);
        return a == null ? 0 : a.get();
    }

    // Try to reserve (decrement) quantity atomically. Returns true if success.
    public boolean tryReserve(String productId, int qty) {
        stock.putIfAbsent(productId, new AtomicInteger(0));
        AtomicInteger a = stock.get(productId);

        while (true) {
            int current = a.get();
            if (current < qty) {
                return false; // not enough
            }
            int updated = current - qty;
            if (a.compareAndSet(current, updated)) {
                return true; // reserved
            }
            // else retry
        }
    }

    // For demo: add stock
    public void addStock(String productId, int qty) {
        stock.putIfAbsent(productId, new AtomicInteger(0));
        stock.get(productId).getAndAdd(qty);
    }
}