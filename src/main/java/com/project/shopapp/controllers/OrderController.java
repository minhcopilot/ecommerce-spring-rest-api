package com.project.shopapp.controllers;

import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.dtos.response.ApiResponse;
import com.project.shopapp.dtos.response.OrderResponse;
import com.project.shopapp.services.OrderService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    OrderService orderService;

    @PostMapping("")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDTO orderDTO,
                                         BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                List<String> errors = bindingResult.getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errors);
            }
            OrderResponse orderResponse = orderService.createOrder(orderDTO);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Order created successfully")
                    .result(orderResponse)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //get all orders
    @GetMapping("")
    public ResponseEntity<?> getAllOrders() {
        try {
            List<OrderResponse> orderResponses = orderService.getAllOrders();
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Orders retrieved successfully")
                    .result(orderResponses)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //get all orders of a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getOrdersByUserId(@Valid @PathVariable("userId") Long userId) {
        try {
            List<OrderResponse> orderResponse = orderService.getOrdersByUserId(userId);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Order retrieved successfully")
                    .result(orderResponse)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //get order by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@Valid @PathVariable("id") Long id) {
        try {
            OrderResponse orderResponse = orderService.getOrderById(id);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Order retrieved successfully")
                    .result(orderResponse)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //Update order by id
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@Valid @PathVariable("id") Long id, @RequestBody OrderDTO orderDTO,
                                         BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                List<String> errors = bindingResult.getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errors);
            }
            OrderResponse orderResponse = orderService.updateOrder(id, orderDTO);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Order updated successfully")
                    .result(orderResponse)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //Delete order by id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@Valid @PathVariable("id") Long id) {
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Order deleted successfully")
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
