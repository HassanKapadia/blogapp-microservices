package com.hancy.comment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "article-service", fallback = ArticleServiceFallback.class)
public interface ArticleServiceClient {

  @GetMapping("/api/articles/{id}")
  public Object getArticleById(@PathVariable("id") Long id);
}
