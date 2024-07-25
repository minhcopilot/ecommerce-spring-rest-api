package com.project.shopapp.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    @JsonProperty("fullname")
    String fullName;
    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number is required")
    String phoneNumber;
    String address;
    @JsonProperty("date_of_birth")
    Date dateOfBirth;
    @JsonProperty("facebook_account_id")
    int facebookAccountId;
    @JsonProperty("google_account_id")
    int googleAccountId;
    @JsonProperty("role_id")
    long roleId;
}
