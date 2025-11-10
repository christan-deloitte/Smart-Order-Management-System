package com.payment.paymentmanagement.Model;

import java.util.List;

public class OrderResponse {
    private Long id;
    private List<ProductEntry> products;
    private String paymentMode;
    private Double totalAmount;
    private Long transactionId;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public List<ProductEntry> getProducts() { return products; }
    public void setProducts(List<ProductEntry> products) { this.products = products; }

    public String getPaymentMode() { return paymentMode; }
    public void setPaymentMode(String paymentMode) { this.paymentMode = paymentMode; }

    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }

    public Long getTransactionId() { return transactionId; }
    public void setTransactionId(Long transactionId) { this.transactionId = transactionId; }
}
