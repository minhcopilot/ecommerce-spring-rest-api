package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDTO {
    @JsonProperty("user_id")
    @Min(value = 1, message = "User id must be greater than 0")
    long userId;
    @JsonProperty("fullname")
    String fullName;
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format")
    String email;
    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^(\\+84|0)\\d{9,10}$", message = "Invalid phone number format (ex: +84987654321 or 0987654321)")
    String phoneNumber;
    String address;
    String note;
    @JsonProperty("total_money")
    @Min(value = 1, message = "Total money must be greater than 0")
    Float totalMoney;
    @JsonProperty("payment_method")
    String paymentMethod;
    @JsonProperty("shipping_method")
    String shippingMethod;
    @JsonProperty("shipping_address")
    String shippingAddress;
    @JsonProperty("shipping_date")
    LocalDate shippingDate;
}
