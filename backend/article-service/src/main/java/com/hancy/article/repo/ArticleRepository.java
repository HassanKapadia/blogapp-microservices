package com.hancy.article.repo;

import com.hancy.article.model.Article;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

  List<Article> findByAuthorId(Long authorId);
}
