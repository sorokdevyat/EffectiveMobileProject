package com.ppr.java.effectivemobileproject.mapping.account;

import com.ppr.java.effectivemobileproject.dto.account.BankAccountDto;
import com.ppr.java.effectivemobileproject.model.BankAccount;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class BankAccountMapper {
    public BankAccount fromDtoToAccount(BankAccountDto accountDto){
        BankAccount bankAccount = BankAccount.builder()
                .id(accountDto.getId())
                .username(accountDto.getUsername())
                .password(accountDto.getPassword())
                .balance(accountDto.getBalance())
                .deposit(accountDto.getDeposit())
                .build();
        return bankAccount;
    }
    public BankAccountDto fromAccountToDto(BankAccount account){
        BankAccountDto accountDto = BankAccountDto.builder()
                .id(account.getId())
                .username(account.getUsername())
                .password(account.getPassword())
                .balance(account.getBalance())
                .deposit(account.getDeposit())
                .build();
        if (!ObjectUtils.isEmpty(account.getUser())){
            accountDto.setUserId(account.getUser().getId());
        }
        return accountDto;
    }

}
