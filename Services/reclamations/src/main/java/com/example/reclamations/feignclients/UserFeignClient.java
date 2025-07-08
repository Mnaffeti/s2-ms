package com.example.reclamations.feignclients;

import com.example.reclamations.DTO.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "user-service",url=("${application.config.user-url}")
)
public interface UserFeignClient {

    @GetMapping("/api/users/exits/{user-id}")
    ResponseEntity<Boolean> existsById(@PathVariable("user-id") String id);

    @GetMapping("/api/users/{user-id}")
    ResponseEntity<UserDTO> findById(@PathVariable("user-id") String id);
}