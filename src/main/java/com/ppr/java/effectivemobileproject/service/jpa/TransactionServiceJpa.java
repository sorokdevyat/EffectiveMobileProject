package com.ppr.java.effectivemobileproject.service.jpa;

import com.ppr.java.effectivemobileproject.dto.transaction.TransactionDto;
import com.ppr.java.effectivemobileproject.mapping.transaction.TransactionMapper;
import com.ppr.java.effectivemobileproject.model.Transaction;
import com.ppr.java.effectivemobileproject.repository.TransactionRepository;
import com.ppr.java.effectivemobileproject.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.NoSuchElementException;
@Service
@RequiredArgsConstructor
public class TransactionServiceJpa implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @Override
    @Transactional
    public TransactionDto createTransaction(TransactionDto transactionDto) {
        if (ObjectUtils.isEmpty(transactionDto.getAccountId())){
            throw new NoSuchElementException(String.format("Account of receiver with id=%d not exist",transactionDto.getAccountId()));
        }
        transactionDto.setTimestamp(OffsetDateTime.now());
        transactionRepository.save(transactionMapper.fromDtoToTransaction(transactionDto));
        return transactionDto;
    }

    @Override
    public List<TransactionDto> getAllAccountTransactions(Long accountId) {
        List<Transaction> transactions = transactionRepository.findAllByAccount_Id(accountId);

        return transactions.stream().map(transactionMapper::fromTransactionToDto).toList();
    }
}
