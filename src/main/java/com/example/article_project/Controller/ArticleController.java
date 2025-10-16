package com.example.article_project.Controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.article_project.domain.Article;
import com.example.article_project.dto.ArticleDto;
import com.example.article_project.dto.PageRequestDto;
import com.example.article_project.dto.PageResponseDto;
import com.example.article_project.service.ArticleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/articles")
    public ResponseEntity<PageResponseDto<ArticleDto>> paging(PageRequestDto pageRequestDto){

        PageResponseDto<ArticleDto> pageResponseDto = articleService.paging(pageRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(pageResponseDto);
    }



    // 게시글 등록
    @PostMapping("/articles")
    public ResponseEntity<Map<String, Long>> postArticle(@RequestBody ArticleDto articleDto){
        articleDto.setLocalDate(LocalDateTime.now());
        Long id = articleService.registerArticle(articleDto);
       
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", id));
    } 

    //게시글 상세 조회 : getArticle
    @GetMapping("/article/{id}")
    public ResponseEntity<ArticleDto> getArticle(@PathVariable(value = "id") Long id){
        ArticleDto articleDto = articleService.findByArticle(id);
        System.out.println("here");
        return ResponseEntity.status(HttpStatus.OK).body(articleDto);
    }

    // 게시글 수정
    @PutMapping("/article/{id}")
    public ResponseEntity<String> updateArticle(
        @PathVariable(value = "id") Long articleId,
        @RequestBody ArticleDto articleDto){

            articleDto.setId(articleId);
        articleService.modifyArticle(articleDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("ok");
    }

    @DeleteMapping("/article/{id}")
      public ResponseEntity<String> removeArticle(
        @PathVariable(value = "id") Long articleId){
            
         articleService.removeArticle(articleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }

    
}
