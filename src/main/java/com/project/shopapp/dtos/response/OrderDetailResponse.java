package com.project.shopapp.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailResponse {
    long id;
    @JsonProperty("order_id")
    long orderId;
    @JsonProperty("product_id")
    long productId;
    long price;
    @JsonProperty("number_of_products")
    int numberOfProducts;
    @JsonProperty("total_money")
    long totalMoney;
    String color;
}
