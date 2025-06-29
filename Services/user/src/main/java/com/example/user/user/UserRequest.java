package com.example.user.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UserRequest(
        String id,
        @NotNull(message = "User first name is required")
        String firstname,
        @NotNull(message = "User last name is required")
        String lastname,
        @NotNull(message = "User username is required")
        String username,
        @NotNull(message = "User email is required")
        @Email(message = "User email is not valid")
        String email

) {
}
