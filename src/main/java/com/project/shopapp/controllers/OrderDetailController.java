package com.project.shopapp.controllers;

import com.project.shopapp.dtos.OrderDetailDTO;
import com.project.shopapp.dtos.response.ApiResponse;
import com.project.shopapp.dtos.response.OrderDetailResponse;
import com.project.shopapp.models.OrderDetail;
import com.project.shopapp.services.OrderDetailService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/order-details")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderDetailController {
    OrderDetailService orderDetailService;
    //create order detail
    @PostMapping("")
    public ResponseEntity<?> createOrderDetail(@Valid @RequestBody OrderDetailDTO orderDetailDTO) {
        try {
            OrderDetailResponse orderDetail = orderDetailService.createOrderDetail(orderDetailDTO);
            return ResponseEntity.ok(ApiResponse.builder()
                    .result(orderDetail)
                    .message("Order detail created successfully")
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //get order detail by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetailById(@Valid @PathVariable Long id) {
        try {
            OrderDetailResponse orderDetail = orderDetailService.getOrderDetail(id);
            return ResponseEntity.ok(ApiResponse.builder()
                    .result(orderDetail)
                    .message("Get order detail successfully")
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //get list order detail by order id
    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetailByOrderId(@Valid @PathVariable Long orderId) {
        try {
            List<OrderDetailResponse> orderDetails = orderDetailService.getOrderDetails(orderId);
            return ResponseEntity.ok(ApiResponse.builder()
                    .result(orderDetails)
                    .message("Get order details successfully")
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //update order detail by id
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(@Valid @PathVariable Long id, @RequestBody OrderDetailDTO orderDetailDTO) {
        try {
            OrderDetailResponse orderDetail = orderDetailService.updateOrderDetail(id, orderDetailDTO);
            return ResponseEntity.ok(ApiResponse.builder()
                    .result(orderDetail)
                    .message("Order detail updated successfully")
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //delete order detail by id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetail(@Valid @PathVariable Long id) {
        try {
            orderDetailService.deleteOrderDetail(id);
            return ResponseEntity.ok("Order detail deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}