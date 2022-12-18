package com.lm.myagenda.controllers;

import com.lm.myagenda.dto.OrderDTO;
import com.lm.myagenda.models.Order;

import com.lm.myagenda.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/api")
public class OrderController {

    @Autowired
    OrderService orderService;

    @RequestMapping(value="/admin/order/all", method = RequestMethod.GET)
    public ResponseEntity<List<Order>> findAllAdmin(){
        List<Order> orderList = orderService.findAll();
        return ResponseEntity.ok().body(orderList);
    }

    @GetMapping("/order/all")
    public ResponseEntity<List<OrderDTO>> findAll(){
        List<Order> orderList = orderService.findAll();
        List<OrderDTO> orderDTOList = orderList.stream().map(obj -> new OrderDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(orderDTOList);
    }
}
