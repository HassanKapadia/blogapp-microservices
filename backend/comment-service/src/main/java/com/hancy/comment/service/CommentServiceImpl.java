package com.hancy.comment.service;

import com.hancy.comment.client.ArticleServiceClient;
import com.hancy.comment.client.UserServiceClient;
import com.hancy.comment.dto.CommentRequest;
import com.hancy.comment.dto.CommentResponse;
import com.hancy.comment.mapper.CommentMapper;
import com.hancy.comment.model.Comment;
import com.hancy.comment.repo.CommentRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepo;
  private final UserServiceClient userClient;
  private final ArticleServiceClient articleClient;

  @Autowired
  public CommentServiceImpl(
      CommentRepository commentRepo,
      UserServiceClient userClient,
      ArticleServiceClient articleClient) {
    this.commentRepo = commentRepo;
    this.userClient = userClient;
    this.articleClient = articleClient;
  }

  @Override
  @Transactional
  public CommentResponse create(CommentRequest req) {
    if (userClient.getUserById(req.getCommentorId()) == null) {
      throw new RuntimeException("Commentor not found");
    }

    if (articleClient.getArticleById(req.getArticleId()) == null) {
      throw new RuntimeException("Article not found");
    }

    Comment comment = new Comment();
    comment.setContent(req.getContent());
    comment.setArticleId(req.getArticleId());
    comment.setCommentorId(req.getCommentorId());
    comment = commentRepo.save(comment);

    return CommentMapper.toResponse(comment);
  }

  @Override
  public List<CommentResponse> getByArticleId(Long articleId) {
    return commentRepo.findByArticleId(articleId).stream()
        .map(CommentMapper::toResponse)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public CommentResponse update(Long id, CommentRequest req, Long authUserId) {
    Comment comment =
        commentRepo.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));

    if (articleClient.getArticleById(req.getArticleId()) == null) {
      throw new RuntimeException("Article not found");
    }

    if (!comment.getCommentorId().equals(authUserId)) {
      throw new RuntimeException("You are not authorized to update this comment");
    }

    if (req.getContent() != null) {
      comment.setContent(req.getContent());
    }

    return CommentMapper.toResponse(commentRepo.save(comment));
  }

  @Override
  @Transactional
  public void delete(Long id, Long authUserId) {
    Comment comment =
        commentRepo.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));

    if (!comment.getCommentorId().equals(authUserId)) {
      throw new RuntimeException("You are not authorized to delete this comment");
    }

    commentRepo.deleteById(id);
  }

  @Override
  @Transactional
  public void deleteByArticleId(Long articleId) {
    if (articleClient.getArticleById(articleId) == null) {
      throw new RuntimeException("Article not found");
    }

    List<Comment> commentList = commentRepo.findByArticleId(articleId);
    if (!commentList.isEmpty()) {
      commentRepo.deleteAll(commentList);
    }
  }

  @Override
  public CommentResponse getById(Long id) {
    Comment comment =
        commentRepo.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));

    return CommentMapper.toResponse(comment);
  }
}
