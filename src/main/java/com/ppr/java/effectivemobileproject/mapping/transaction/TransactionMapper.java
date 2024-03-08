package com.ppr.java.effectivemobileproject.mapping.transaction;

import com.ppr.java.effectivemobileproject.dto.transaction.TransactionDto;
import com.ppr.java.effectivemobileproject.model.BankAccount;
import com.ppr.java.effectivemobileproject.model.Transaction;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class TransactionMapper {
    public Transaction fromDtoToTransaction(TransactionDto transactionDto){
        Transaction transaction = Transaction.builder()
                .id(transactionDto.getId())
                .amount(transactionDto.getAmount())
                .timestamp(transactionDto.getTimestamp())
                .build();
        if (!ObjectUtils.isEmpty(transactionDto.getAccountId())){
            transaction.setAccount(BankAccount.builder().id(transactionDto.getAccountId()).build());
        }
        return transaction;
    }
    public TransactionDto fromTransactionToDto(Transaction transaction){
        TransactionDto transactionDto = TransactionDto.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .timestamp(transaction.getTimestamp())
                .build();
        if (!ObjectUtils.isEmpty(transaction.getAccount())){
            transactionDto.setAccountId(transaction.getAccount().getId());
        }

        return transactionDto;
    }
}
