package com.ppr.java.effectivemobileproject.service;

import com.ppr.java.effectivemobileproject.dto.account.BankAccountDto;

public interface BankAccountService {

    BankAccountDto save(BankAccountDto accountDto);
    BankAccountDto findById(Long id);
    BankAccountDto update(BankAccountDto bankAccountDto);
    void everyMinuteIncrease();
    BankAccountDto transaction(Long receiverAccountId, Long senderAccountId, Long amount);
}
