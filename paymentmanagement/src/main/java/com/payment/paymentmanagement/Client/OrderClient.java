package com.payment.paymentmanagement.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.payment.paymentmanagement.Model.OrderResponse;

@FeignClient(name = "order-service", url = "http://localhost:9292")
public interface OrderClient {

    @GetMapping("/orders/{id}")
    OrderResponse getOrderById(@PathVariable Long id);
}
