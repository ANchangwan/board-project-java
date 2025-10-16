package com.example.article_project;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.query.JpaQueryMethod;

import com.example.article_project.domain.Article;
import com.example.article_project.domain.QArticle;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ArticleTest {

    @Autowired
    private EntityManager em;

    @Test
    void test(){
        Article article = Article.builder()
                            .title("Querydsl")
                            .contents("Querydsl 테스트 예제입니다.")
                            .writer("kso")
                            .build();
        
        em.persist(article);

        //q 클래스
        QArticle qArticle = QArticle.article;

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        List<Article>articles  =  queryFactory
                            .selectFrom(qArticle)
                            .where(qArticle.title.contains("title").and(qArticle.contents.contains("content")))
                            .orderBy(qArticle.id.desc())
                            .fetch();

        log.info("article.first.id : {}", articles.get(0).getId());
        log.info("article.first.id : {}", articles.get(articles.size() - 1).getId());

        log.info("articles : {}",articles.size());
        //when

        assertThat(articles).isNotNull();
    }

    @Test
    void test1(){

        Long articleId = 1L;

        QArticle qArticle = QArticle.article;

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);


        //given
        Article article = queryFactory.selectFrom(qArticle).where(qArticle.id.eq(articleId)).fetchOne();
        //whne

        //then

        assertThat(article).isNotNull();
    }


    
}
