package com.ppr.java.effectivemobileproject.dto.userFilter;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserFilter {
    private String phone;
    private String fullName;
    private String email;
    private LocalDate dateOfBirth;
}
