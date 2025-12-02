package com.hancy.article.service;

import com.hancy.article.client.CommentServiceClient;
import com.hancy.article.client.UserServiceClient;
import com.hancy.article.dto.ArticleRequest;
import com.hancy.article.dto.ArticleResponse;
import com.hancy.article.mapper.ArticleMapper;
import com.hancy.article.model.Article;
import com.hancy.article.repo.ArticleRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleServiceImpl implements ArticleService {

  private final ArticleRepository articleRepo;
  private final UserServiceClient userClient;
  private final CommentServiceClient commentClient;

  @Autowired
  public ArticleServiceImpl(
      ArticleRepository articleRepo,
      UserServiceClient userClient,
      CommentServiceClient commentClient) {
    this.articleRepo = articleRepo;
    this.userClient = userClient;
    this.commentClient = commentClient;
  }

  @Override
  @Transactional
  public ArticleResponse create(ArticleRequest req) {
    Object user = userClient.getUserById(req.getAuthorId());
    if (user == null) {
      throw new RuntimeException("Author not found");
    }

    Article article = new Article();
    article.setTitle(req.getTitle());
    article.setContent(req.getContent());
    article.setAuthorId(req.getAuthorId());
    article = articleRepo.save(article);

    return ArticleMapper.toResponse(article);
  }

  @Override
  public List<ArticleResponse> listAll() {
    return articleRepo.findAll().stream()
        .map(ArticleMapper::toResponse)
        .collect(Collectors.toList());
  }

  @Override
  public ArticleResponse get(Long id) {
    Article article =
        articleRepo.findById(id).orElseThrow(() -> new RuntimeException("Article not found"));
    return ArticleMapper.toResponse(article);
  }

  @Override
  @Transactional
  public ArticleResponse update(Long id, ArticleRequest req, Long authUserId) {
    Article article =
        articleRepo.findById(id).orElseThrow(() -> new RuntimeException("Article not found"));

    if (!article.getAuthorId().equals(authUserId)) {
      throw new RuntimeException("You are not authorized to update this article");
    }

    if (req.getTitle() != null) {
      article.setTitle(req.getTitle());
    }

    if (req.getContent() != null) {
      article.setContent(req.getContent());
    }

    return ArticleMapper.toResponse(articleRepo.save(article));
  }

  @Override
  @Transactional
  public void delete(Long id, Long authUserId) {
    Article article =
        articleRepo.findById(id).orElseThrow(() -> new RuntimeException("Article not found"));
    if (!article.getAuthorId().equals(authUserId)) {
      throw new RuntimeException("You are not authorized to delete this article");
    }

    commentClient.deleteByArticleId(id);
    articleRepo.deleteById(id);
  }
}
