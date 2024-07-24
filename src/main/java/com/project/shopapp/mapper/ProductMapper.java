package com.project.shopapp.mapper;

import com.project.shopapp.dtos.ProductDTO;
import com.project.shopapp.dtos.response.ProductResponse;
import com.project.shopapp.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toProduct(ProductDTO productDTO);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    void updateProductFromDto(ProductDTO productDTO, @MappingTarget Product product);
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    default ProductResponse toProductResponse(Product product) {
        if (product == null) {
            return null;
        }
        ProductResponse.ProductResponseBuilder builder = ProductResponse.builder()
                .name(product.getName())
                .price(product.getPrice())
                .thumbnail(product.getThumbnail())
                .description(product.getDescription())
                .categoryId(product.getCategory() != null ? product.getCategory().getId() : null);

        // Ánh xạ createdAt và updatedAt một cách rõ ràng
        ProductResponse response = builder.build();
        response.setCreatedAt(product.getCreatedAt());
        response.setUpdatedAt(product.getUpdatedAt());

        return response;
    }
}
