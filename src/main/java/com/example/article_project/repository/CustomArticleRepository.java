package com.example.article_project.repository;


import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import com.example.article_project.domain.Article;
import com.example.article_project.dto.ArticleSearchConditon;

public interface CustomArticleRepository{

    Page<Article> search(ArticleSearchConditon condition, Pageable pageable);
}