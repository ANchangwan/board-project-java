package com.example.article_project.service;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.article_project.domain.Article;
import com.example.article_project.dto.ArticleDto;
import com.example.article_project.dto.PageRequestDto;
import com.example.article_project.dto.PageResponseDto;
import com.example.article_project.repository.ArticleRepository;


import lombok.RequiredArgsConstructor;


@Transactional(readOnly = false)
@RequiredArgsConstructor
@Service
public class ArticleServiceImpl implements ArticleService {
    
    private final ArticleRepository articleRepository;

    // 게시글 등록
    @Override
    @Transactional
    public Long registerArticle(ArticleDto articleDto) {
        Article article = dtoToentity(articleDto);

        articleRepository.save(article);

        return article.getId();
    }

    // private Article dtoToEntity(ArticleDto dto) {
    //     return Article.builder()
    //             .title(dto.getTitle())
    //             .contents(dto.getContents())
    //             .writer(dto.getWriter())
    //             .build();
    // }

    // 게시글 상세 조회
    @Override
    public ArticleDto findByArticle(Long id) {
        return articleRepository.findById(id)
                .map(article -> entityToDto(article))
                .orElseThrow(() ->{
            return new IllegalArgumentException(id + "에 해당하는 게시글 정보가 존재하지 않습니다.");
      });

    //   ArticleDto articleDto = entityToDto(article);
    }

    // 게시글 수정
    @Transactional(readOnly = false)
    @Override
    public void modifyArticle(ArticleDto articleDto) {

     Article article = articleRepository.findById(articleDto.getId())
        .orElseThrow(() -> new IllegalArgumentException(articleDto.getId() + "에 해당하는 게시글이 없습니다.!"));

        article.setWriter(articleDto.getWriter());
        article.setContents(articleDto.getContents());
        article.setTitle(articleDto.getTitle());
      
    }

    // 게시글 삭제
    @Override
    @Transactional(readOnly = false)
    public void removeArticle(Long id) {
        articleRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException(id + "해당 게시물이 존재하지 않습니다."));


        articleRepository.deleteById(id);
    }

    @Override
    public PageResponseDto<ArticleDto> paging(PageRequestDto pageRequestDto) {
        Pageable pageable = PageRequest.of(
                                            pageRequestDto.getPage() - 1, 
                                            pageRequestDto.getSize(),
                                            Sort.by("id").descending());

        
        Page<Article> page = articleRepository.findAll(pageable);

        List<ArticleDto> posts = page.getContent().stream()
            .map(article -> entityToDto(article))
            .collect(Collectors.toList());
        
        int totalCount = (int)page.getTotalElements();    

        return PageResponseDto.<ArticleDto>builder()
                    .dtoList(posts)
                    .pageRequestDto(pageRequestDto)
                    .totalCount(totalCount)
                    .build();
    }
}
