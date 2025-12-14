package com.hancy.auth.client;

import com.hancy.blogapp.common.dto.CreateUserRequest;
import com.hancy.blogapp.common.dto.UserAuthResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service")
public interface UserServiceClient {

  @GetMapping("/api/users/internal/by-email")
  UserAuthResponse getUserByEmail(@RequestParam String email);

  @PostMapping("/api/users/internal")
  UserAuthResponse create(@RequestBody CreateUserRequest req);
}
