package com.project.shopapp.mapper;

import com.project.shopapp.dtos.OrderDetailDTO;
import com.project.shopapp.dtos.response.OrderDetailResponse;
import com.project.shopapp.models.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {
     OrderDetail toOrderDetail(OrderDetailDTO orderDetailDTO);
     @Mapping(target = "id", ignore = true)
        void updateOrderDetailFromDto(OrderDetailDTO orderDetailDTO,@MappingTarget OrderDetail orderDetail);
    @Mapping(source = "order.id", target = "orderId")
    @Mapping(source = "product.id", target = "productId")
     OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail);
}
