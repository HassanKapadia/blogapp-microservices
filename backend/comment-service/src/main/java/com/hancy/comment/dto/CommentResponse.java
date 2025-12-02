package com.hancy.comment.dto;

import java.time.LocalDateTime;

public class CommentResponse {

  private Long id;
  private String content;
  private Long articleId;
  private Long commentorId;
  private LocalDateTime createdOn;
  private LocalDateTime updatedOn;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Long getArticleId() {
    return articleId;
  }

  public void setArticleId(Long articleId) {
    this.articleId = articleId;
  }

  public Long getCommentorId() {
    return commentorId;
  }

  public void setCommentorId(Long commentorId) {
    this.commentorId = commentorId;
  }

  public LocalDateTime getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(LocalDateTime createdOn) {
    this.createdOn = createdOn;
  }

  public LocalDateTime getUpdatedOn() {
    return updatedOn;
  }

  public void setUpdatedOn(LocalDateTime updatedOn) {
    this.updatedOn = updatedOn;
  }
}
