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
    public PageResponseDto(List<T> dtoList, PageRequestDto pageRequestDto, int totalCount) {
        this.dtoList = dtoList;
        this.pageRequestDto = pageRequestDto;
        this.totalCount = totalCount;

        this.currentPage = pageRequestDto.getPage();
        this.size = pageRequestDto.getSize();

        // 한 블록당 표시할 페이지 수 (예: 10)
        int pageBlockSize = 10;

        // 현재 페이지가 속한 블록의 마지막 페이지 번호
        end = (int) Math.ceil((double) currentPage / pageBlockSize) * pageBlockSize;

        // 현재 블록의 시작 페이지 번호
        start = end - (pageBlockSize - 1);

        // 전체 페이지 수
        totalPage = (int) Math.ceil((double) totalCount / size);

        // 마지막 페이지 초과 시 end 조정
        if (end > totalPage) {
            end = totalPage;
        }

        // 이전 / 다음 블록 존재 여부
        prev = start > 1;
        next = totalCount > (end * size);

        // 페이지 번호 목록 생성
        pageNumList = IntStream.rangeClosed(start, end)
                .boxed()
                .collect(Collectors.toList());

        // 이전 / 다음 페이지 번호 계산
        prevPage = prev ? start - 1 : 0;
        nextPage = next ? end + 1 : 0; // end - 1 → end + 1 로 수정

        // 디버깅 로그
        log.info("currentPage: {}, size: {}, totalCount: {}", currentPage, size, totalCount);
        log.info("start: {}, end: {}, totalPage: {}", start, end, totalPage);
        log.info("prev: {}, next: {}", prev, next);
        log.info("pageNumList: {}", pageNumList);
        log.info("prevPage: {}, nextPage: {}", prevPage, nextPage);
    }


}