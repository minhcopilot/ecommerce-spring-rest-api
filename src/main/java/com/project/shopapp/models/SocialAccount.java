package com.project.shopapp.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "social_accounts")
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SocialAccount {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    Long id;

    @Column(name = "provider",nullable = false,length = 50)
    String provider;
    @Column(name = "provider_id",nullable = false,length = 50)
    String providerId;

    @Column(name = "email")
    String email;

    @Column(name = "name")
    String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
