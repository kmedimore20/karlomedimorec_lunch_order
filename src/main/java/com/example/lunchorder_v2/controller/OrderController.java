package com.example.lunchorder_v2.controller;

import com.example.lunchorder_v2.model.Order;
import com.example.lunchorder_v2.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/order")
    public ResponseEntity<Order> createOrder(@RequestBody Order order){
        try {
            Order _order = orderRepository.save(new Order(order.getMenuItems()));
            return new ResponseEntity<>(_order, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/order/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable("id") long id, @RequestBody Order order){
        Optional<Order> orderData = orderRepository.findById(id);

        if (orderData.isPresent()){
            Order _order = orderData.get();
            _order.setMenuItems(order.getMenuItems());
            return new ResponseEntity<>(orderRepository.save(_order), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
