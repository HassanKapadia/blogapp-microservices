package com.hancy.article.mapper;

import com.hancy.article.dto.ArticleResponse;
import com.hancy.article.model.Article;

public class ArticleMapper {

  public static ArticleResponse toResponse(Article article) {
    ArticleResponse articleResponse = new ArticleResponse();
    articleResponse.setId(article.getId());
    articleResponse.setTitle(article.getTitle());
    articleResponse.setContent(article.getContent());
    articleResponse.setAuthorId(article.getAuthorId());
    articleResponse.setCreatedOn(article.getCreatedOn());
    articleResponse.setUpdatedOn(article.getUpdatedOn());
    return articleResponse;
  }
}
