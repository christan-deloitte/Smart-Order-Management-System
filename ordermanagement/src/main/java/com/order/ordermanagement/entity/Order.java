package com.order.ordermanagement.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.order.ordermanagement.model.ProductEntry;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(
        name = "order_products",
        joinColumns = @JoinColumn(name = "order_id")
    )
    @AttributeOverrides({
        @AttributeOverride(name = "productId",
                           column = @Column(name = "product_id")),
        @AttributeOverride(name = "quantity",
                           column = @Column(name = "quantity"))
    })
    private List<ProductEntry> products = new ArrayList<>();

    private String status;
    private Double totalAmount;
    private Long transactionId;
    private String paymentMode;

    public Order() {
    }

    public Order(Long id, List<ProductEntry> products, String status, Double totalAmount, Long transactionId) {
        this.id = id;
        this.products = products;
        this.status = status;
        this.totalAmount = totalAmount;
        this.transactionId = transactionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ProductEntry> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntry> products) {
        this.products = products;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return (id != null) ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Order{" +
            "id=" + id +
            ", products=" + products +
            ", status='" + status + '\'' +
            ", totalAmount=" + totalAmount +
            ", transactionId='" + transactionId + '\'' +
            '}';
    }
}
