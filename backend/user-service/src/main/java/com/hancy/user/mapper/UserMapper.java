package com.hancy.user.mapper;

import com.hancy.user.dto.UserResponse;
import com.hancy.user.model.User;

public class UserMapper {

  public static UserResponse toResponse(User user) {
    UserResponse userResponse = new UserResponse();
    userResponse.setId(user.getId());
    userResponse.setName(user.getName());
    userResponse.setUsername(user.getUsername());
    userResponse.setEmail(user.getEmail());
    userResponse.setBio(user.getBio());
    userResponse.setCreatedOn(user.getCreatedOn());
    userResponse.setUpdatedOn(user.getUpdatedOn());
    return userResponse;
  }
}
