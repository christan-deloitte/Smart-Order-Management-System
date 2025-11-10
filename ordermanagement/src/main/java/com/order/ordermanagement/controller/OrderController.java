package com.order.ordermanagement.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.order.ordermanagement.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.order.ordermanagement.model.OrderDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto.Response> getMethodName(@PathVariable Long id ) {
        return new ResponseEntity<>(orderService.getOrderDetails(id),HttpStatus.OK);
    }

    @PostMapping(consumes="application/json",produces="application/json")
    public ResponseEntity<OrderDto.Response> createOrder(@RequestBody OrderDto.Request orderRequest) {
    OrderDto.Response createdOrder = orderService.createOrder(orderRequest);
    return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
}

    


    
}
