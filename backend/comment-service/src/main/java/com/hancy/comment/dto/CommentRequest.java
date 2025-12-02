package com.hancy.comment.dto;

public class CommentRequest {

  private Long articleId;
  private Long commentorId;
  private String content;

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

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
