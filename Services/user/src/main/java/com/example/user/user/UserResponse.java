package com.example.user.user;

public record UserResponse(
        String id,
        String firstname,
        String lastname,
        String username,
        String email
) {
}