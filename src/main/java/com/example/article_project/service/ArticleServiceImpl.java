package com.example.article_project.service;

import org.springframework.stereotype.Service;

import com.example.article_project.domain.Article;
import com.example.article_project.dto.ArticleDto;
import com.example.article_project.repository.ArticleRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    @Override
    @Transactional
    public Long registerArticle(ArticleDto articleDto) {
        Article article = dtoToEntity(articleDto);

        articleRepository.save(article);

        return article.getId();
    }
    private Article dtoToEntity(ArticleDto dto) {
        return Article.builder()
                .title(dto.getTitle())
                .contents(dto.getContents())
                .writer(dto.getWriter())
                .build();
    }
    
}
