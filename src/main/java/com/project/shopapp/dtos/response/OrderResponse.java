package com.project.shopapp.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse{
    Long id;

    @JsonProperty("user_id")
    long userId;

    @JsonProperty("fullname")
    String fullName;

    String email;

    @JsonProperty("phone_number")
    String phoneNumber;

    String address;

    String note;
    @JsonProperty("order_date")
    Date orderDate;

    String status;

    @JsonProperty("total_money")
    Float totalMoney;

    @JsonProperty("payment_method")
    String paymentMethod;

    @JsonProperty("shipping_method")
    String shippingMethod;

    @JsonProperty("shipping_address")
    String shippingAddress;

    @JsonProperty("shipping_date")
    Date shippingDate;

    @JsonProperty("tracking_number")
    String trackingNumber;
    Boolean active;

}
