package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDTO {
    @NotBlank(message = "name must be required")
    @Size(min =3, max = 200, message = "name must be between 3 and 200 characters")
    String name;
    @Min(value = 0, message = "Price must be greater than or equal to 0")
            @Max(value = 10000000, message = "Price must be less than or equal to 10.000.000")
    Float price;
    String thumbnail;
    String description;
    @JsonProperty("category_id")
    Long categoryId;
}
