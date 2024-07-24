package com.project.shopapp.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "tokens")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Token {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    Long id;

    @Column(name = "token",nullable = false)
    String token;

    @Column(name = "token_type",nullable = false,length = 50)
    String tokenType;

    @Column(name = "expiration_date",nullable = false)
    LocalDateTime expiryDate;

    Boolean revoked;
    Boolean expired;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

}
