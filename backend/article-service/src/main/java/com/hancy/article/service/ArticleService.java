package com.hancy.article.service;

import com.hancy.article.dto.ArticleRequest;
import com.hancy.article.dto.ArticleResponse;
import java.util.List;

public interface ArticleService {

  public ArticleResponse create(ArticleRequest req);

  public List<ArticleResponse> listAll();

  public ArticleResponse get(Long id);

  public ArticleResponse update(Long id, ArticleRequest req, Long authUserId);

  public void delete(Long id, Long authUserId);
}
