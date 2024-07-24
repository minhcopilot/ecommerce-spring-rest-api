package com.project.shopapp.repositories;

import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.dtos.ProductDTO;
import com.project.shopapp.models.Order;
import com.project.shopapp.models.Product;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>{
    List<Order> findByUserId(Long userId);




}
