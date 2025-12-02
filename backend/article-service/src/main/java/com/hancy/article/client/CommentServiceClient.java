package com.hancy.article.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "comment-service")
public interface CommentServiceClient {

  @DeleteMapping("/api/comments/by-article/{articleId}")
  public void deleteByArticleId(@PathVariable("articleId") Long articleId);
}
