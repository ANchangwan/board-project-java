package com.example.article_project.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.example.article_project.domain.Article;
import com.example.article_project.dto.ArticleSearchConditon;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.example.article_project.domain.QArticle;


@Repository
public class CustomArticleRepositoryImpl implements CustomArticleRepository{

    @PersistenceContext
    private EntityManager em;

    private QArticle qArticle = QArticle.article;

    private JPAQueryFactory jpaQueryFactory;


    public CustomArticleRepositoryImpl(EntityManager em) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Article> search(ArticleSearchConditon condition, Pageable pageable) {
        // 검색을 하지 않는 경우
        if(condition.getTitle() == "" && condition.getWriter() == "" && condition.getContents() == ""){

            List<Article> articles = jpaQueryFactory
                    .selectFrom(qArticle)
                    .where(
                        writerLike(condition.getWriter())
                            .and(titleLike(condition.getTitle()))
                            .and(contentsLike(condition.getContents()))
                    )
                    .orderBy(qArticle.id.desc())
                    .offset(pageable.getPageNumber() * pageable.getPageSize())
                    .limit(pageable.getPageSize())
                    .fetch();

                
                Long totalCount = jpaQueryFactory
                                    .select(qArticle.count())
                                    .from(qArticle)
                                    .fetchOne();
            return null;
        }else{
            List<Article> articles = jpaQueryFactory
                    .selectFrom(qArticle)
                    .where(
                        writerLike(condition.getWriter())
                            .and(titleLike(condition.getTitle()))
                            .and(contentsLike(condition.getContents()))
                    )
                    .orderBy(qArticle.id.desc())
                    .offset(pageable.getPageNumber() * pageable.getPageSize())
                    .limit(pageable.getPageSize())
                    .fetch();

                
                long totalCount = jpaQueryFactory
                                    .select(qArticle.count())
                                    .from(qArticle)
                                    .where(
                                        writerLike(condition.getWriter()).
                                        and(titleLike(condition.getTitle()))
                                        .and(contentsLike(condition.getContents()))
                                    ).fetchOne();

             return PageableExecutionUtils.getPage(articles, pageable, () -> totalCount);                       
        }
        
    }

    private BooleanExpression writerLike(String writer){
        return writer == null ? null : qArticle.writer.contains(writer);
    }

    private BooleanExpression titleLike(String title){
        return title == null ? null : qArticle.writer.contains(title);
    }

    private BooleanExpression contentsLike(String contents){
        return contents == null ? null : qArticle.writer.contains(contents);
    }


    
}
