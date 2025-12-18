package com.hancy.user.web;

import com.hancy.blogapp.common.constants.BlogAppHeaderConstants;
import com.hancy.blogapp.common.dto.CreateUserRequest;
import com.hancy.blogapp.common.dto.UserAuthResponse;
import com.hancy.user.dto.UpdateUserRequest;
import com.hancy.user.dto.UserResponse;
import com.hancy.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/internal")
  public ResponseEntity<UserAuthResponse> create(@RequestBody CreateUserRequest req) {
    UserAuthResponse createdUser = userService.createUser(req);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
  }

  @GetMapping("/internal/by-email")
  public ResponseEntity<UserAuthResponse> getUserByEmail(@RequestParam("email") String email) {
    UserAuthResponse user = userService.getUserByEmail(email);
    return ResponseEntity.ok(user);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserResponse> get(@PathVariable("id") Long id) {
    UserResponse user = userService.getUserById(id);
    return ResponseEntity.ok(user);
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserResponse> update(
      @PathVariable("id") Long id,
      @RequestBody UpdateUserRequest req,
      @RequestHeader(BlogAppHeaderConstants.AUTH_USER_ID) Long authUserId) {
    UserResponse updatedUser = userService.updateUser(id, req, authUserId);
    return ResponseEntity.ok(updatedUser);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(
      @PathVariable("id") Long id,
      @RequestHeader(BlogAppHeaderConstants.AUTH_USER_ID) Long authUserId) {
    userService.deleteUser(id, authUserId);
    return ResponseEntity.noContent().build();
  }
}
