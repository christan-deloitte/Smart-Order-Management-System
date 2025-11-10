package com.order.ordermanagement.service;

import com.order.ordermanagement.client.PaymentClient;
import com.order.ordermanagement.client.ProductClient;
import com.order.ordermanagement.entity.Order;
import com.order.ordermanagement.model.*;
import com.order.ordermanagement.repository.OrderRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private PaymentClient paymentClient;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private OrderRepository orderRepository;

    public OrderDto.Response createOrder(OrderDto.Request orderRequest) {
        double totalAmount = 0.0;

        // Calculate total amount from Product Service
        for (var entry : orderRequest.getProducts()) {
        ApiResponse<ProductResponse> productApiResponse = productClient.getProductById(entry.getProductId());
        ProductResponse productData = productApiResponse.getData();

        if (productData == null) {
        throw new RuntimeException("Product not found for ID: " + entry.getProductId());
        }

        if (productData.getQuantity() < entry.getQuantity()) {
        throw new RuntimeException("Stock not available for product ID: " + entry.getProductId());
        }
        double price = productData.getPrice();
        totalAmount += price * entry.getQuantity();
    }

        // Save order with initial status
        Order order = new Order();
        order.setProducts(orderRequest.getProducts());
        order.setPaymentMode(orderRequest.getPaymentMode());
        order.setTotalAmount(totalAmount);
        order.setStatus("PAYMENT_PENDING");

        Order savedOrder = orderRepository.save(order);

        // Process payment
        PaymentRequest paymentRequest = new PaymentRequest(
                savedOrder.getId(),
                orderRequest.getPaymentMode(),
                totalAmount
        );

        ApiResponse<PaymentResponse> paymentResponse = paymentClient.processPayment(paymentRequest);
        if (paymentResponse != null && paymentResponse.getData() != null) {
            for (var entry : orderRequest.getProducts()) {
             productClient.reduceProductQuantity(entry.getProductId(), entry.getQuantity());
            }
        savedOrder.setTransactionId(paymentResponse.getData().getId());
        savedOrder.setStatus("PAYMENT_COMPLETED");
    } else {
        savedOrder.setStatus("PAYMENT_FAILED");
    }
        orderRepository.save(savedOrder);

        return new OrderDto.Response(
                savedOrder.getId(),
                savedOrder.getProducts(),
                savedOrder.getPaymentMode(),
                savedOrder.getTotalAmount(),
                savedOrder.getTransactionId()
        );
    }

    public OrderDto.Response getOrderDetails(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return new OrderDto.Response(
                order.getId(),
                order.getProducts(),
                order.getPaymentMode(),
                order.getTotalAmount(),
                order.getTransactionId()
        );
    }
}
