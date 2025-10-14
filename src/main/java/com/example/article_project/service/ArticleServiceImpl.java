package com.example.article_project.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.article_project.domain.Article;
import com.example.article_project.dto.ArticleDto;
import com.example.article_project.repository.ArticleRepository;


import lombok.RequiredArgsConstructor;


@Transactional(readOnly = false)
@RequiredArgsConstructor
@Service
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
    @Override
    public ArticleDto findByArticle(Long id) {
        return articleRepository.findById(id)
                .map(article -> entityToDto(article))
                .orElseThrow(() ->{
            return new IllegalArgumentException(id + "에 해당하는 게시글 정보가 존재하지 않습니다.");
      });

    //   ArticleDto articleDto = entityToDto(article);

 
    }
    @Transactional(readOnly = false)
    @Override
    public void modifyArticle(ArticleDto articleDto) {

     Article article = articleRepository.findById(articleDto.getId())
        .orElseThrow(() -> new IllegalArgumentException(articleDto.getId() + "에 해당하는 게시글이 없습니다.!"));

        article.setWriter(articleDto.getWriter());
        article.setContents(articleDto.getContents());
        article.setTitle(articleDto.getTitle());
      
    }

    
    @Override
    @Transactional(readOnly = false)
    public void removeArticle(Long id) {
        articleRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException(id + "해당 게시물이 존재하지 않습니다."));


        articleRepository.deleteById(id);
    }


 
    
    
}
