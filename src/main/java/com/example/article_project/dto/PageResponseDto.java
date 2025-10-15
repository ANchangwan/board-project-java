package com.example.article_project.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class PageResponseDto<T>{
    private List<T> dtoList;

    private PageRequestDto pageRequestDto;

    private int totalPage;

    private boolean prev = false, next = false;

    private int start = 0, end = 0;

    private int prevPage = 0, nextPage = 0, totalCount = 0, currentPage = 0;

    private int pageSize = 10;

    private int size = 0;

    private List<Integer> pageNumList = new ArrayList<>();


    @Builder
    public PageResponseDto(List<T> dtoList, PageRequestDto pageRequestDto, int totalCount){
        this.dtoList = dtoList;
        this.pageRequestDto = pageRequestDto;
        this.totalCount = totalCount;

        this.currentPage = pageRequestDto.getPage();

        this.size = pageRequestDto.getSize();

        // 현재 페이지 번호가 숙련 페이지 블록의 마지막 페이지 번호 계산
        end = (int)Math.ceil(currentPage / (double) pageSize) * pageSize;

        log.info("end : {}", end);

        start = end - (pageSize - 1);

        log.info("start : {}", end);

        // 총 페이지 수중 계산한다.
        int lastPage = (int) Math.ceil(totalCount /(double) size);

        this.totalPage = lastPage;
        end = end > lastPage ? lastPage : end;

        this.prev = start > 1;

        this.next = totalCount > (end * size);
        log.info("next : {}", next);

        this.pageNumList = IntStream.range(start, end + 1).boxed().collect(Collectors.toList());
        log.info("pageNumList : {}", pageNumList);

        this.prevPage = prev ? start - 1 : 0;
        log.info("prevPage : {}", prevPage);

        this.nextPage = next ? end - 1 : 0;
        log.info("nextPage : {}", nextPage);
    }

}