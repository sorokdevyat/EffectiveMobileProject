package com.ppr.java.effectivemobileproject.dto.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.OffsetDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private Long accountId;
    private Long amount;
    private OffsetDateTime timestamp;

    public TransactionDto(Long accountId, Long amount, OffsetDateTime timestamp) {
        this.accountId = accountId;
        this.amount = amount;
        this.timestamp = timestamp;
    }
}

