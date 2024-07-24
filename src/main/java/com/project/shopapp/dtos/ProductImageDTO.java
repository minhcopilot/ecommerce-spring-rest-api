package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductImageDTO {
    @JsonProperty("product_id")
    @Min(value = 1, message = "Product id must be greater than 0")
    Long productId;
    @JsonProperty("image_url")
    @Size(min = 1, message = "Image url must not be empty")
    String imageUrl;
}
