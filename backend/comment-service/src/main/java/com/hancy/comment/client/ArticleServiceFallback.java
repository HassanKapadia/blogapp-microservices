package com.hancy.comment.client;

import org.springframework.stereotype.Component;

@Component
public class ArticleServiceFallback implements ArticleServiceClient {

  @Override
  public Object getArticleById(Long id) {
    return null;
  }
}
