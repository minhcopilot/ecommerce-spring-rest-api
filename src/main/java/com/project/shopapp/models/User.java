package com.project.shopapp.models;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "fullname",nullable = false,length = 100)
    String fullName;

    @Column(name = "phone_number",nullable = false,length = 10)
    String phoneNumber;

    @Column(name = "address",length = 300)
    String address;

    @Column(name ="password",length = 200)
    String password;

    @Column(name ="is_active")
    Boolean isActive;

    @Column(name = "date_of_birth")
    Date dateOfBirth;

    @Column(name = "facebook_account_id")
    Long facebookAccountId;

    @Column(name = "google_account_id")
    Long googleAccountId;

    @ManyToOne
    @JoinColumn(name = "role_id")
    Role role;
}
