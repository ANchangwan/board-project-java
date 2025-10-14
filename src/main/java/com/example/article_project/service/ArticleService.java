package com.example.article_project.service;

import com.example.article_project.domain.Article;
import com.example.article_project.dto.ArticleDto;

public interface ArticleService {

      // 게시글 등록
      Long registerArticle(ArticleDto article);

      // 게시글 상세 조회
      ArticleDto findByArticle(Long id);

      //게시글 수정
      void modifyArticle(ArticleDto articleDto);

      //게시글 삭제
      void removeArticle(Long id);


      default Article dtoToentity(ArticleDto articleDto){
        return Article.builder()
        .title(articleDto.getTitle())
        .contents(articleDto.getContents())
        .writer(articleDto.getWriter())
        .regDate(articleDto.getLocalDate())
        .build();
      }

      default ArticleDto entityToDto(Article article){
        return ArticleDto.builder()
        .id(article.getId())
        .title(article.getTitle())
        .contents(article.getContents())
        .writer(article.getWriter())
        .build();
      }
}