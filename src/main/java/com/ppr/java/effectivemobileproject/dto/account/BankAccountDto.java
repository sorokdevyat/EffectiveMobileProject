package com.ppr.java.effectivemobileproject.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private String username;
    private String password;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long balance;
    private Long deposit;
    @NotNull
    private Long userId;
}
