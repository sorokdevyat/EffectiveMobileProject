package com.ppr.java.effectivemobileproject.service;

import com.ppr.java.effectivemobileproject.dto.transaction.TransactionDto;

import java.util.List;

public interface TransactionService {
    TransactionDto createTransaction(TransactionDto transactionDto);
    List<TransactionDto> getAllAccountTransactions(Long id);

}
