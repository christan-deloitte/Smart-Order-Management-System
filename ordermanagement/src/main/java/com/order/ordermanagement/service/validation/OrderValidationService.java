package com.order.ordermanagement.service.validation;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.order.ordermanagement.model.OrderDto;

@Slf4j
@Service
public class OrderValidationService {

    public void validateOrder(OrderDto.Response existingOrderDto, OrderDto.Response newOrderDto){

        if(newOrderDto == null){
            //log.info("Either existing or new Order object is null");
            throw new IllegalArgumentException("new data cannot be null");
        }
        if(existingOrderDto == null ){
            //log.info("Either existing or new Order object is null");
            throw new IllegalArgumentException("Existing data can not be null");
        }
        //Validate if Id is different
        if(existingOrderDto.getId() != newOrderDto.getId()){
            //log.info("Either existing or new Order object is null");
            throw new IllegalArgumentException("Id does not match");
        }
    }
}