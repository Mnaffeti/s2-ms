package com.example.user.user;

import org.springframework.stereotype.Service;

@Service
public class UserMapper {


    public User toUser(UserRequest request) {
        if (request == null) return null;

        return User.builder()
                .id(request.id())
                .firstname(request.firstname())
                .lastname(request.lastname())
                .username(request.username())
                .email(request.email())
                .build();
    }

    public UserResponse fromUser(User User) {
        return new UserResponse(User.getId(),
                User.getFirstname(),
                User.getLastname(),
                User.getUsername(),
                User.getEmail());
    }
}
