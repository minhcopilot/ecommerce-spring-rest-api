package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailDTO {
    @JsonProperty("order_id")
    @Min(value = 1, message = "Order id must be greater than 0")
    long orderId;
    @JsonProperty("product_id")
    @Min(value = 1, message = "Product id must be greater than 0")
    long productId;
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    long price;
    @JsonProperty("number_of_products")
    @Min(value = 1, message = "Number of products must be greater than 0")
    int numberOfProducts;
    @JsonProperty("total_money")
    @Min(value = 0, message = "total money must be greater than or equal to 0")
    long totalMoney;
    String color;

}
