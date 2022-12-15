package com.lm.myagenda.controllers;

import com.lm.myagenda.models.Order;

import com.lm.myagenda.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/api")
public class OrderController {

    @Autowired
    OrderService orderService;
    
    @RequestMapping(value="/order/all", method = RequestMethod.GET)
    public ResponseEntity<List<Order>> test(){
        List<Order> orderList = orderService.findAll();
        return ResponseEntity.ok().body(orderList);
    }
}
