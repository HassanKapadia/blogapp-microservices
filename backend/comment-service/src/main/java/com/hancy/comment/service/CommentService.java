package com.hancy.comment.service;

import com.hancy.comment.dto.CommentRequest;
import com.hancy.comment.dto.CommentResponse;
import java.util.List;

public interface CommentService {

  public CommentResponse create(CommentRequest req);

  public List<CommentResponse> getByArticleId(Long articleId);

  public CommentResponse update(Long id, CommentRequest req, Long authUserId);

  public void delete(Long id, Long authUserId);

  public void deleteByArticleId(Long articleId);
}
