package com.ppr.java.effectivemobileproject.dto;

import com.ppr.java.effectivemobileproject.dto.userFilter.UserFilter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageRequestDto {
    private int page;
    private int size;
    private UserFilter filter;
}
