package com.hancy.user.web;

import com.hancy.user.dto.CreateUserRequest;
import com.hancy.user.dto.UpdateUserRequest;
import com.hancy.user.dto.UserResponse;
import com.hancy.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  public UserResponse create(@RequestBody CreateUserRequest req) {
    return userService.createUser(req);
  }

  @GetMapping("/{id}")
  public UserResponse get(@PathVariable("id") Long id) {
    return userService.getUserById(id);
  }

  @PutMapping("/{id}")
  public UserResponse update(
      @PathVariable("id") Long id,
      @RequestBody UpdateUserRequest req,
      @RequestHeader("X-Auth-UserId") Long authUserId) {
    return userService.updateUser(id, req, authUserId);
  }

  @DeleteMapping("/{id}")
  public String delete(
      @PathVariable("id") Long id, @RequestHeader("X-Auth-UserId") Long authUserId) {
    userService.deleteUser(id, authUserId);
    return "User deleted successfully";
  }
}
