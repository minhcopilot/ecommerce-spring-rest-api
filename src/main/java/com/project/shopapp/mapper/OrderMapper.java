package com.project.shopapp.mapper;


import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.dtos.response.OrderResponse;
import com.project.shopapp.models.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderMapper{

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Order toOrder(OrderDTO orderDTO);
    @Mapping(source = "user.id", target = "userId")
    OrderResponse toOrderResponse(Order order);
    @Mapping(target = "id", ignore = true)
    void updateOrderFromDTO(OrderDTO orderDTO, @MappingTarget Order order);
}
