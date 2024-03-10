package com.ppr.java.effectivemobileproject.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private String fullname;
    private Set<String> phoneNumbers;
    private Set<String> emails;
    private LocalDate dateOfBirth;
}
