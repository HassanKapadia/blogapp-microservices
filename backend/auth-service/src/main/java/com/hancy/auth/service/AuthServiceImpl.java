package com.hancy.auth.service;

import com.hancy.auth.client.UserServiceClient;
import com.hancy.auth.dto.AuthRequest;
import com.hancy.auth.dto.AuthResponse;
import com.hancy.blogapp.common.dto.CreateUserRequest;
import com.hancy.blogapp.common.dto.UserAuthResponse;
import com.hancy.blogapp.common.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

  private final UserServiceClient userClient;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  @Autowired
  public AuthServiceImpl(
      UserServiceClient userClient, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
    this.userClient = userClient;
    this.passwordEncoder = passwordEncoder;
    this.jwtUtil = jwtUtil;
  }

  @Override
  public AuthResponse login(AuthRequest request) {
    UserAuthResponse user = userClient.getUserByEmail(request.getEmail());
    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      throw new RuntimeException("Invalid credentials");
    }

    String token = jwtUtil.generateToken(user.getId(), user.getEmail());
    return new AuthResponse(token, user.getId(), user.getEmail());
  }

  @Override
  public AuthResponse signup(CreateUserRequest request) {
    // Hashing password prior to user creation
    request.setPassword(passwordEncoder.encode(request.getPassword()));

    UserAuthResponse user = userClient.create(request);
    String token = jwtUtil.generateToken(user.getId(), user.getEmail());

    return new AuthResponse(token, user.getId(), user.getEmail());
  }
}
