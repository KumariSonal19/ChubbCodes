package com.example.orderproc;

import java.time.Instant;
import java.util.UUID;

public class Order {
    public enum Status { NEW, PROCESSING, SUCCESS, FAILED, OUT_OF_STOCK }

    private final String orderId;
    private final String productId;
    private final int quantity;
    private final double price;
    private final Instant createdAt;
    private Status status;
    private String failureReason;

    public Order(String productId, int quantity, double price) {
        this.orderId = UUID.randomUUID().toString();
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.createdAt = Instant.now();
        this.status = Status.NEW;
    }

    // Getters / setters
    public String getOrderId() { return orderId; }
    public String getProductId() { return productId; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public Instant getCreatedAt() { return createdAt; }
    public synchronized Status getStatus() { return status; }
    public synchronized void setStatus(Status status) { this.status = status; }
    public synchronized String getFailureReason() { return failureReason; }
    public synchronized void setFailureReason(String r) { this.failureReason = r; }

    @Override
    public String toString() {
        return orderId + "," + productId + "," + quantity + "," + price + "," + createdAt + "," + status + (failureReason==null?"":(","+failureReason));
    }
}