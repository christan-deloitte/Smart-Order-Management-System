package com.order.ordermanagement.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.order.ordermanagement.model.ApiResponse;
import com.order.ordermanagement.model.ProductResponse;

@FeignClient(name = "product-service", url = "http://localhost:9393")
public interface ProductClient {

    @GetMapping("/products/{id}")
    ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable long id);


    
    
}
 