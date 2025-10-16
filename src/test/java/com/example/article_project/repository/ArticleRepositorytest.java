package com.example.article_project.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import org.assertj.core.api.Assertions;


import com.example.article_project.domain.Article;
import com.example.article_project.domain.Attachment;
import com.example.article_project.dto.ArticleSearchConditon;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ArticleRepositorytest {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    @Rollback(false)
    void saveArticeleAndFile(){
        List<Attachment> files = new ArrayList<>();
        files.add(new Attachment("a.txt","/upload", 100));
        files.add(new Attachment("b.txt","/upload", 200));
        files.add(new Attachment("c.txt","/upload", 300));

        Article article = Article.builder()
        .title("title")
        .contents("contents")
        .writer("writer")
        .files(files)
        .build();

        articleRepository.save(article);

       Long id = article.getId();

       assertThat(article.getId()).isNotNull();
    
    }

    

    @Test
    void testFindArticleById(){
        // given
        Long articleId = 2L;
        
        // when
        Article article = articleRepository.findArticleById(articleId);

        log.info("id : {}",article.getId());
        log.info("title : {}", article.getTitle());
        log.info("contents : {}", article.getContents());


        //then
        assertThat(article.getId()).isEqualTo(2L);
        // assertThat(article.getFiles()).hasSize(3);
    }

      @Test
    @Rollback(false)
    void registerArticle_savesArticleAndReturnsId() {

        List<Article> articles = new ArrayList<>();
        for(int i =1; i <= 125; i++){
            Article article = Article.builder()
            .title("title " + i )
            .contents("contents " + i)
            .writer("writer " + i)
            .regDate(LocalDateTime.now())
            .build();

            articles.add(article);
        }
        System.out.println("article size : " + articles.size());
        articleRepository.saveAll(articles);

        Assertions.assertThat(articles).hasSize(125);


    }

    @Test
    void TestGetFileCount(){
        Long articleId = 2L;

        //when
        int fileCount = articleRepository.getFileCount(articleId);

        log.info("fileCount : {}", fileCount);
    }

    @Test
    void testFindArticleWithfirstFile(){
        Long articleId = 3L;

        Article article = articleRepository.findArticleWithFirstFile(articleId);

        assertThat(article.getId()).isNotNull();

        assertThat(article.getFiles()).hasSize(1); 
    }

    @Test
    void testSearch() {
        ArticleSearchConditon condition = new ArticleSearchConditon();
        condition.setWriter("writer");

        Pageable pageable = PageRequest.of(0, 10);
        Page<Article> page = articleRepository.search(condition, pageable);

        log.info("게시글 수 : {}", page.getTotalElements());
    }

    
}
