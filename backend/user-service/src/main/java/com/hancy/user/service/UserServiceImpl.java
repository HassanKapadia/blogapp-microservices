package com.hancy.user.service;

import com.hancy.blogapp.common.dto.CreateUserRequest;
import com.hancy.blogapp.common.dto.UserAuthResponse;
import com.hancy.user.dto.UpdateUserRequest;
import com.hancy.user.dto.UserResponse;
import com.hancy.user.mapper.UserMapper;
import com.hancy.user.model.User;
import com.hancy.user.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepo;

  @Autowired
  public UserServiceImpl(UserRepository userRepo) {
    this.userRepo = userRepo;
  }

  @Override
  @Transactional
  public UserAuthResponse createUser(CreateUserRequest req) {
    if (userRepo.existsByEmail(req.getEmail())) {
      throw new RuntimeException("User with this email already exists");
    }

    if (userRepo.existsByUsername(req.getUsername())) {
      throw new RuntimeException("User with this username already exists");
    }

    User user = new User();
    user.setName(req.getName());
    user.setUsername(req.getUsername());
    user.setEmail(req.getEmail());
    user.setBio(req.getBio());
    user.setPassword(req.getPassword());

    userRepo.save(user);

    return new UserAuthResponse(
        user.getId(), user.getEmail(), user.getUsername(), user.getPassword());
  }

  @Override
  @Transactional
  public UserResponse updateUser(Long id, UpdateUserRequest req, Long authUserId) {
    if (!id.equals(authUserId)) {
      throw new RuntimeException("You can update only your own account");
    }

    User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

    if (req.getName() != null) {
      user.setName(req.getName());
    }

    if (req.getBio() != null) {
      user.setBio(req.getBio());
    }

    userRepo.save(user);

    return UserMapper.toResponse(user);
  }

  @Override
  public UserResponse getUserById(Long id) {
    User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    return UserMapper.toResponse(user);
  }

  @Override
  public UserAuthResponse getUserByEmail(String email) {
    User user =
        userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    return new UserAuthResponse(
        user.getId(), user.getEmail(), user.getUsername(), user.getPassword());
  }

  @Override
  @Transactional
  public void deleteUser(Long id, Long authUserId) {
    if (!id.equals(authUserId)) {
      throw new RuntimeException("You cannot delete another user's account");
    }

    if (!userRepo.existsById(id)) {
      throw new RuntimeException("User not found");
    }

    userRepo.deleteById(id);
  }
}
