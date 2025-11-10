package com.order.ordermanagement.model;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class OrderDto {

    private String paymentMode;
    private List<ProductEntry> products;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private List<ProductEntry> products;
        private String paymentMode;
    }

    public List<ProductEntry> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntry> products) {
        this.products = products;
    }

    public String getPaymentMode() {
        return paymentMode;
    }
    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
    private Long id;
    private List<ProductEntry> products;
    
    private String paymentMode;
    private Double totalAmount;
    private Long transactionId;
    }
}
