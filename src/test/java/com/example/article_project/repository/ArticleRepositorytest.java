package com.example.article_project.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.example.article_project.domain.Article;
import com.example.article_project.domain.Attachment;

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
    
}
