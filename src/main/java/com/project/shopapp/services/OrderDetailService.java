package com.project.shopapp.services;

import com.project.shopapp.dtos.OrderDetailDTO;
import com.project.shopapp.dtos.response.OrderDetailResponse;
import com.project.shopapp.mapper.OrderDetailMapper;
import com.project.shopapp.mapper.OrderMapper;
import com.project.shopapp.models.Order;
import com.project.shopapp.models.OrderDetail;
import com.project.shopapp.models.Product;
import com.project.shopapp.repositories.OrderDetailRepository;
import com.project.shopapp.repositories.OrderRepository;
import com.project.shopapp.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class OrderDetailService {
    OrderDetailRepository orderDetailRepository;
    ProductRepository productRepository;
    OrderRepository orderRepository;
    OrderDetailMapper orderDetailMapper;
    public OrderDetailResponse createOrderDetail(OrderDetailDTO orderDetailDTO) {
        //check if order exists
        Order order = orderRepository.findById(orderDetailDTO.getOrderId()).orElseThrow(() -> new RuntimeException("Order not found"));
        //check if product exists
        Product product = productRepository.findById(orderDetailDTO.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));
        OrderDetail orderDetail = orderDetailMapper.toOrderDetail(orderDetailDTO);

        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
        orderDetail.setTotalMoney((float) (orderDetailDTO.getNumberOfProducts() * orderDetailDTO.getPrice()));
        return orderDetailMapper.toOrderDetailResponse(orderDetailRepository.save(orderDetail));
    }
    public OrderDetailResponse getOrderDetail(Long orderDetailId) {
        return orderDetailRepository.findById(orderDetailId).map(orderDetailMapper::toOrderDetailResponse)
                .orElseThrow(() -> new RuntimeException("Order detail not found"));
    }
    public List<OrderDetailResponse> getOrderDetails(Long orderId) {
        //check orderDetail exists
        if(orderDetailRepository.findById(orderId).isEmpty()) {
            throw new RuntimeException("Order detail not found");
        }
        List<OrderDetail> orderDetails = orderDetailRepository.findAllByOrderId(orderId);
        return orderDetails.stream()
                .map(orderDetailMapper::toOrderDetailResponse)
                .collect(java.util.stream.Collectors.toList());
    }
    public OrderDetailResponse updateOrderDetail(Long orderDetailId, OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId).orElseThrow(() -> new RuntimeException("Order detail not found"));
        //check if order exists
        Order order = orderRepository.findById(orderDetailDTO.getOrderId()).orElseThrow(() -> new RuntimeException("Order not found"));
        //check if product exists
        Product product = productRepository.findById(orderDetailDTO.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));

        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
        // Update fields from DTO
        orderDetailMapper.updateOrderDetailFromDto(orderDetailDTO, orderDetail);

        // Calculate totalMoney after updating other fields
        orderDetail.setTotalMoney((float) (orderDetail.getNumberOfProducts() * orderDetail.getPrice()));

        return orderDetailMapper.toOrderDetailResponse(orderDetailRepository.save(orderDetail));
    }
    public void deleteOrderDetail(Long orderDetailId) {
        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId).orElseThrow(() -> new RuntimeException("Order detail not found"));
        orderDetailRepository.delete(orderDetail);
    }
}
