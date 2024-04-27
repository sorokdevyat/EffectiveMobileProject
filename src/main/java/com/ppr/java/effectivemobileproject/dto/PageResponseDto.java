package com.ppr.java.effectivemobileproject.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PageResponseDto<T> {
    private int page;
    private int total;
    private int size; // размер стр
    private List<T> responsePage;
}
