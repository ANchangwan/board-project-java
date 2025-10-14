package com.example.article_project.service;

import com.example.article_project.domain.Article;
import com.example.article_project.dto.ArticleDto;
import com.example.article_project.repository.ArticleRepository;

import lombok.extern.slf4j.Slf4j;

import org.assertj.core.api.Assertions;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;




@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ArticleServiceImplTest {

    @Autowired
    private ArticleService articleService;

    @Test
    @Rollback(false)
    void registerArticle_savesArticleAndReturnsId() {

        List<Article> articles = new ArrayList<>();
        for(int i =0; i < 100; i++){
            Article article = Article.builder()
            .title("title" + i )
            .contents("contents" + i)
            .writer("writer" + i)
            .regDate(LocalDateTime.now())
            .build();

            articles.add(article);
        }
        System.out.println("article size : " + articles.size());
        articleRepository.saveAll(articles);

        Assertions.assertThat(articles).hasSize(100);


    }

    @Test
    void testFindById(){
        Long articleId = 10L;
        Optional<Article> found = articleRepository.findById(articleId);

        if(found.isPresent()){
            Article article = found.get();
            log.info("id : {}", article.getId());
            log.info("title : {}", article.getTitle());
            log.info("writer : {}", article.getWriter());
            log.info("contents : {}", article.getContents());
        } else {
            log.info("{}에 해당하는 게시글이 존재하지 않습니다!", articleId);
        }
    }

    @Test
    void testFindById1(){
        //given
        Long articleId = 2L;

        //when
        assertThatThrownBy(() ->{
            Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException(articleId + "에 해당하는 게시글이 존재하지 않습니다."));

             log.info("id : {}", article.getId());
            log.info("title : {}", article.getTitle());
            log.info("writer : {}", article.getWriter());
            log.info("contents : {}", article.getContents());
        }).isInstanceOf(IllegalArgumentException.class);

        
    }

    @Test
    void testRetrieveArticle(){
        Long articleId = 102L;
        ArticleDto articleDto = articleService.findByArticle(102L);

        // log.info("article : {}", articleDto);

        assertThat(articleDto).isNotNull();
        // assertThat(articleDto.getId()).isSameAs(10L);
    }

    //게시글 수정
    @Test
    @Rollback(false)
    void testUpdate(){
        // given
        Article article =  articleRepository.findById(102L).get();

        if(article == null) System.out.println("djq");

        //when
        article.setTitle("title 수정");
        article.setWriter("writer 수정");
        article.setContents("contents 수정");
        

        //then
        assertThat(article.getTitle()).isEqualTo("title 수정");
        assertThat(article.getWriter()).isEqualTo("writer 수정");
    }

    @Test
    @Rollback(false)
    void testModifyArticle(){
        //given
        ArticleDto articleDto = ArticleDto.builder()
        .id(1L)
        .title("title 11")
        .writer("writer11")
        .contents("content11")
        .build();
         
        //when
        articleService.modifyArticle(articleDto);

        //
        ArticleDto found =  articleService.findByArticle(1L);
    }

    @Test
    @Rollback(false)
    void testDelete() {
        Long articleId = 101L;
        articleRepository.deleteById(articleId);

        assertThatThrownBy(() ->{
           articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException(articleId + "에 해당하는 게시글이 존재하지 않습니다."));

        }).isInstanceOf(IllegalArgumentException.class);


        assertThat(articleServiceImpl.findByArticle(articleId)).isNull();
    }
}