package com.order.ordermanagement.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.order.ordermanagement.client.PaymentClient;
import com.order.ordermanagement.client.ProductClient;
import com.order.ordermanagement.entity.Order;
import com.order.ordermanagement.model.ApiResponse;
import com.order.ordermanagement.model.OrderDto;
import com.order.ordermanagement.model.PaymentRequest;
import com.order.ordermanagement.model.PaymentResponse;
import com.order.ordermanagement.model.ProductEntry;
import com.order.ordermanagement.model.ProductResponse;
import com.order.ordermanagement.repository.OrderRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderService {

    @Autowired
    ProductClient productClient;

    @Autowired
    PaymentClient paymentClient;

    @Autowired
    OrderRepository orderRepository;

    public OrderDto getOrderDetails(Long id) {
        // TODO Auto-generated method stub
        Order order = orderRepository.findById(id).orElseThrow(()-> new RuntimeException("Order not found"));
        System.out.println("Order fetched: " + order.toString());
        return convertEntityToDto(order);
    }

    public OrderDto convertEntityToDto(Order order){
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setProducts(order.getProducts());
        orderDto.setStatus(order.getStatus());
        orderDto.setTotalAmount(order.getTotalAmount());
        orderDto.setTransactionId(order.getTransactionId());
        return orderDto;    
    }  
    
    public Order convertDtoToEntity(OrderDto orderDto){
        Order order = new Order();
        order.setProducts(orderDto.getProducts());
        order.setStatus(orderDto.getStatus());
        order.setTotalAmount(orderDto.getTotalAmount());
        return order;    
    }

    public OrderDto createOrder(OrderDto orderDto) {


        //OrderValidation
        // Verify all products exist and are available
        boolean validProducts = orderDto.getProducts().stream().allMatch(x -> {
            ResponseEntity<ApiResponse<ProductResponse>> response = productClient.getProductById(x.getProductId());
            return response.getStatusCode().value() == 200;
        });

        boolean itemsinStock = orderDto.getProducts().stream().allMatch(x -> {
            ResponseEntity<ApiResponse<ProductResponse>> response = productClient.getProductById(x.getProductId());
            if (response.getStatusCode().value() == 200) {
                ProductResponse product = response.getBody().getData();
                return product.getQuantity() >= x.getQuantity();
            }
            return false;
        });

        if (!validProducts) throw new IllegalArgumentException("Products not found");
        if (!itemsinStock) throw new IllegalArgumentException("Products not in stock");


        // ((entry as ProductEntry)=>{
        //     ResponseEntity<ApiResponse<ProductResponse>> response = productClient.getProductById(entry.getProductId());
        //     if(response.getStatusCode()!==200) itemnotinstock=true;
        // })

        Order order = convertDtoToEntity(orderDto);
        order.setStatus("WAITING FOR PAYMENT");
        Order savedOrder = orderRepository.save(order);


        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setOrderId(savedOrder.getId());
        paymentRequest.setAmount(savedOrder.getTotalAmount());
        paymentRequest.setPaymentMode("ONLINE");


        // Call Payment Service to process payment
        ApiResponse<PaymentResponse> paymentResponse = paymentClient.processPayment(paymentRequest);
        System.out.println("Payment Response: " + paymentResponse.getStatus());

        savedOrder.setTransactionId(paymentResponse.getData().getId());
        System.out.println("Transaction ID set in order: " + paymentResponse.getData().getId());
        savedOrder.setStatus(paymentResponse.getData().getStatus().equals("SUCCESS") ? "ORDER PLACED" : "PAYMENT FAILED");
        Order updatedOrder = orderRepository.save(savedOrder);

        return convertEntityToDto(updatedOrder);
    }
    
}
