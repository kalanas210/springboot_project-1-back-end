package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.Order;
import org.example.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/order/api/v1")
public class OrderController {
    private final OrderService orderService;
    @PostMapping("/save")
    ResponseEntity<Order> saveOrder(@RequestBody Order order){
        try {
            Order savedOrder=orderService.saveOrder(order);
            return new ResponseEntity<>(savedOrder,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
