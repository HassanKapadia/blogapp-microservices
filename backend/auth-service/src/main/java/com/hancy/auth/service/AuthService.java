package com.hancy.auth.service;

import com.hancy.auth.dto.AuthRequest;
import com.hancy.auth.dto.AuthResponse;
import com.hancy.blogapp.common.dto.CreateUserRequest;

public interface AuthService {

  public AuthResponse login(AuthRequest request);

  public AuthResponse signup(CreateUserRequest request);
}
