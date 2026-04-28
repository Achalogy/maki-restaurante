package com.maki.web.controller;

import com.maki.web.entities.OrderDetails;
import com.maki.web.service.OrderDetailsService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order-details")
public class OrderDetailsController {

    @Autowired
    private OrderDetailsService orderDetailsService;

    @GetMapping("/{id}")
    public ResponseEntity<List<OrderDetails>> getOrderDetailsById(@PathVariable Long id) {
        List<OrderDetails> orderDetails = orderDetailsService.findByOrderId(id);
        return new ResponseEntity<>(orderDetails, HttpStatus.OK);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<OrderDetails>> getOrderDetailsByClientId(@PathVariable Long clientId) {
        List<OrderDetails> orderDetails = orderDetailsService.findByClientId(clientId);
        return new ResponseEntity<>(orderDetails, HttpStatus.OK);
    }
}
