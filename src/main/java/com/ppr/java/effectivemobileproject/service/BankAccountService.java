package com.ppr.java.effectivemobileproject.service;

import com.ppr.java.effectivemobileproject.dto.account.BankAccountDto;
import com.ppr.java.effectivemobileproject.model.BankAccount;

import java.math.BigDecimal;
import java.util.List;

public interface BankAccountService {
    BankAccountDto findByUsername(String username);

    BankAccountDto save(BankAccountDto accountDto);
    BankAccountDto findById(Long id);
    BankAccountDto update(BankAccountDto bankAccountDto);
    void everyMinuteIncrease();
    BankAccountDto transaction(Long receiverAccountId, Long senderAccountId, Long amount);
}
