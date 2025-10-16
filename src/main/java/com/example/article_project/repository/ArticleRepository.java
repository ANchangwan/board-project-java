package com.example.article_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.article_project.domain.Article;


@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>, CustomArticleRepository {

    @Query("select a from Article AS a where a.id = :articleId")
    Article findArticleById(@Param("articleId") Long id);

    @Query("select size(a.files) from Article a where a.id = :articleId")
    int getFileCount(@Param("articleId") Long id);
    
    @Query("select a from Article a join a.files f where a.id = :articleId and index(f) = 0")
    Article findArticleWithFirstFile(@Param("articleId") Long id);

    // @Query("Select a from Article a join a.files where a.id = :articleId and Index(f) = 0")
    // Article findArticleWithFirstFile(@Param("articleId") Long id);

    @Query("select distinct a from Article a join a.files f where a.id = :articleId")
    Article findArticle(@Param("articleId") Long id);
    
}
