package com.hancy.user.service;

import com.hancy.blogapp.common.dto.CreateUserRequest;
import com.hancy.blogapp.common.dto.UserAuthResponse;
import com.hancy.user.dto.UpdateUserRequest;
import com.hancy.user.dto.UserResponse;

public interface UserService {

  public UserAuthResponse createUser(CreateUserRequest req);

  public UserResponse updateUser(Long id, UpdateUserRequest req, Long authUserId);

  public UserResponse getUserById(Long id);

  public UserAuthResponse getUserByEmail(String email);

  public void deleteUser(Long id, Long authUserId);
}
