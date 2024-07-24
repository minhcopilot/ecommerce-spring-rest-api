package com.project.shopapp.services;

import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.dtos.response.OrderResponse;
import com.project.shopapp.mapper.OrderMapper;
import com.project.shopapp.models.Order;
import com.project.shopapp.models.OrderStatus;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.OrderRepository;
import com.project.shopapp.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {
    UserRepository userRepository;
    OrderRepository orderRepository;
    OrderMapper orderMapper;

    public OrderResponse createOrder(OrderDTO orderDTO) {
       try {
           // Check if user exists
           User user = userRepository.findById(orderDTO.getUserId())
                   .orElseThrow(() -> new RuntimeException("User not found"));
           //check shipping date
           LocalDate shippingDate = orderDTO.getShippingDate() == null ? LocalDate.now() : orderDTO.getShippingDate();
           if(shippingDate.isBefore(LocalDate.now())) {
               throw new RuntimeException("Shipping date must be in the future");
           }
           Order order = orderMapper.toOrder(orderDTO);
           order.setShippingDate(shippingDate);
           order.setUser(user);
           order.setOrderDate(new Date());
           order.setStatus(OrderStatus.PENDING);
           order.setActive(true);
           orderRepository.save(order);
           return orderMapper.toOrderResponse(order);
       }catch (Exception e) {
           throw new RuntimeException(e.getMessage());
       }
    }
    //get all orders
    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(orderMapper::toOrderResponse)
                .collect(Collectors.toList());
    }
    //get order
    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return orderMapper.toOrderResponse(order);
    }

    //get all orders by user
    public List<OrderResponse> getOrdersByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Order> orders = orderRepository.findByUserId(user.getId());
        return orders.stream()
                .map(orderMapper::toOrderResponse)
                .collect(Collectors.toList());
    }

    //update order
    public OrderResponse updateOrder(Long id, OrderDTO orderDTO) {
        try {
            Order order = orderRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Order not found"));
            // Check if user exists
            User user = userRepository.findById(orderDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            orderMapper.updateOrderFromDTO(orderDTO, order);
            if(orderDTO.getShippingDate() != null && orderDTO.getShippingDate().isBefore(LocalDate.now())) {
                throw new RuntimeException("Shipping date must be in the future");
            }

            orderRepository.save(order);
            return orderMapper.toOrderResponse(order);
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    //delete order
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setActive(false);
        orderRepository.save(order);
    }
}
