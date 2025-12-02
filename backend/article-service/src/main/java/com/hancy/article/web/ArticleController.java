package com.hancy.article.web;

import com.hancy.article.dto.ArticleRequest;
import com.hancy.article.dto.ArticleResponse;
import com.hancy.article.service.ArticleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/articles")
public class ArticleController {

  private final ArticleService articleService;

  @Autowired
  public ArticleController(ArticleService articleService) {
    this.articleService = articleService;
  }

  @PostMapping
  public ResponseEntity<ArticleResponse> create(@RequestBody ArticleRequest req) {
    return ResponseEntity.ok(articleService.create(req));
  }

  @GetMapping
  public ResponseEntity<List<ArticleResponse>> listAll() {
    return ResponseEntity.ok(articleService.listAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<ArticleResponse> get(@PathVariable("id") Long id) {
    return ResponseEntity.ok(articleService.get(id));
  }

  @PutMapping("/{id")
  public ResponseEntity<ArticleResponse> update(
      @PathVariable("id") Long id,
      @RequestBody ArticleRequest req,
      @RequestHeader("X-Auth-UserId") Long authUserId) {
    return ResponseEntity.ok(articleService.update(id, req, authUserId));
  }

  @DeleteMapping("/{id")
  public ResponseEntity<ArticleResponse> delete(
      @PathVariable("id") Long id, @RequestHeader("X-Auth-UserId") Long authUserId) {
    articleService.delete(id, authUserId);
    return ResponseEntity.noContent().build();
  }
}
