package com.hancy.user.service;

import com.hancy.user.dto.CreateUserRequest;
import com.hancy.user.dto.UpdateUserRequest;
import com.hancy.user.dto.UserResponse;

public interface UserService {

  public UserResponse createUser(CreateUserRequest req);

  public UserResponse updateUser(Long id, UpdateUserRequest req, Long authUserId);

  public UserResponse getUserById(Long id);

  public void deleteUser(Long id, Long authUserId);
}
