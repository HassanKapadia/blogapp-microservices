package com.hancy.article.client;

import org.springframework.stereotype.Component;

@Component
public class UserServiceFallback implements UserServiceClient {

  @Override
  public Object getUserById(Long id) {
    return null;
  }
}
