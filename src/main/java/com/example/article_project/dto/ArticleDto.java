package com.example.article_project.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto{

    private Long id;
    private String title;
    private String contents;
    private String writer;
    private LocalDateTime localDate;
}