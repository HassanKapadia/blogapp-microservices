package com.hancy.comment.web;

import com.hancy.blogapp.common.constants.BlogAppHeaderConstants;
import com.hancy.comment.dto.CommentRequest;
import com.hancy.comment.dto.CommentResponse;
import com.hancy.comment.service.CommentService;
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
@RequestMapping("/api/comments")
public class CommentController {

  private final CommentService commentService;

  @Autowired
  public CommentController(CommentService commentService) {
    this.commentService = commentService;
  }

  @PostMapping
  public ResponseEntity<CommentResponse> create(CommentRequest req) {
    return ResponseEntity.ok(commentService.create(req));
  }

  @GetMapping("/by-article/{articleId}")
  public ResponseEntity<List<CommentResponse>> listAllByArticle(
      @PathVariable("articleId") Long articleId) {
    return ResponseEntity.ok(commentService.getByArticleId(articleId));
  }

  @PutMapping("/{id}")
  public ResponseEntity<CommentResponse> update(
      @PathVariable("id") Long id,
      @RequestBody CommentRequest req,
      @RequestHeader(BlogAppHeaderConstants.AUTH_USER_ID) Long authUserId) {
    return ResponseEntity.ok(commentService.update(id, req, authUserId));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(
      @PathVariable("id") Long id,
      @RequestHeader(BlogAppHeaderConstants.AUTH_USER_ID) Long authUserId) {
    commentService.delete(id, authUserId);
    return ResponseEntity.noContent().build();
  }

  // This endpoint is called by article-service when an article is deleted
  @DeleteMapping("/by-article/{articleId}")
  public ResponseEntity<Void> deleteAllByArticle(@PathVariable("articleId") Long articleId) {
    commentService.deleteByArticleId(articleId);
    return ResponseEntity.noContent().build();
  }
}
