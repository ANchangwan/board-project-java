package com.example.article_project.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter @Setter
@Table(name = "article")
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String contents;

    private String writer;

    @Column(name = "reg_date")
    private LocalDateTime regDate;

    @ElementCollection
    @CollectionTable(name = "attachment", joinColumns = @JoinColumn(name="id"))
    @OrderColumn(name="order_index")
    @Builder.Default
    List<Attachment> files = new ArrayList<>();



    public void changeContents(String contents){
        this.contents = contents;
    }

    public void changeWriter(String writer){
        this.writer = writer;
    }

    public void changeTitle(String title){
        this.title = title;
    }
}
