package com.example.article_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleSearchConditon {
    private String title;
    private String writer;
    private String contents;
}
