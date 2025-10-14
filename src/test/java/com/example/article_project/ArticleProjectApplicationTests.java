package com.example.article_project;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.article_project.domain.Article;
import com.example.article_project.repository.ArticleRepository;

@SpringBootTest
class ArticleProjectApplicationTests {

		@Autowired
		private ArticleRepository articleRepository;

		@Test
		void testSave(){
			Article article = Article.builder()
			.title("title")
			.contents("content")
			.writer("writer")
			.build();

			
			article.changeContents("1");
			article.changeTitle("채식주의");
			article.changeWriter("hello");
		


		}
	

}
