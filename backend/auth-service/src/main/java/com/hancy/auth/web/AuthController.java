package com.hancy.auth.web;

import com.hancy.auth.dto.AuthRequest;
import com.hancy.auth.dto.AuthResponse;
import com.hancy.auth.service.AuthService;
import com.hancy.blogapp.common.dto.CreateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthService authService;

  @Autowired
  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
    return ResponseEntity.ok(authService.login(request));
  }

  @PostMapping("/signup")
  public ResponseEntity<AuthResponse> signup(@RequestBody CreateUserRequest request) {
    return ResponseEntity.ok(authService.signup(request));
  }
}
