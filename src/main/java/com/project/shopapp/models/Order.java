package com.project.shopapp.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "orders")
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "fullname",nullable = false,length = 100)
    String fullName;

    String email;

    @Column(name = "phone_number",nullable = false,length = 10)
    String phoneNumber;

    @Column(name = "address",nullable = false)
    String address;

    String note;

    @Column(name = "order_date")
    Date orderDate;

    @Column(name = "status",nullable = false)
    String status;

    @Column(name = "total_money")
    Integer totalMoney;

    @Column(name = "payment_method",length = 100)
    String paymentMethod;

    @Column(name = "shipping_method",length = 100)
    String shippingMethod;

    @Column(name = "shipping_address")
    String shippingAddress;

    @Column(name ="shipping_date")
    LocalDate shippingDate;

    @Column(name ="tracking_number")
    String trackingNumber;

    @Column(name = "active")
    Boolean active;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
