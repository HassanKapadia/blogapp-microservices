package com.hancy.comment.mapper;

import com.hancy.comment.dto.CommentResponse;
import com.hancy.comment.model.Comment;

public class CommentMapper {

  public static CommentResponse toResponse(Comment comment) {
    CommentResponse commentResponse = new CommentResponse();
    commentResponse.setId(comment.getId());
    commentResponse.setContent(comment.getContent());
    commentResponse.setArticleId(comment.getArticleId());
    commentResponse.setCommentorId(comment.getCommentorId());
    commentResponse.setCreatedOn(comment.getCreatedOn());
    commentResponse.setUpdatedOn(comment.getUpdatedOn());
    return commentResponse;
  }
}
