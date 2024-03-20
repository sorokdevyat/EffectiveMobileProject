package com.ppr.java.effectivemobileproject.service.jpa;

import com.ppr.java.effectivemobileproject.dto.account.BankAccountDto;
import com.ppr.java.effectivemobileproject.dto.transaction.TransactionDto;
import com.ppr.java.effectivemobileproject.mapping.account.BankAccountMapper;
import com.ppr.java.effectivemobileproject.model.BankAccount;
import com.ppr.java.effectivemobileproject.model.User;
import com.ppr.java.effectivemobileproject.model.exceptions.ImpossibleBalanceException;
import com.ppr.java.effectivemobileproject.model.exceptions.NotUniqueUserException;
import com.ppr.java.effectivemobileproject.model.exceptions.NotUniqueUsernameException;
import com.ppr.java.effectivemobileproject.repository.BankAccountRepository;
import com.ppr.java.effectivemobileproject.service.BankAccountService;
import com.ppr.java.effectivemobileproject.service.TransactionService;
import com.ppr.java.effectivemobileproject.service.UserService;
import lombok.RequiredArgsConstructor;;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.OffsetDateTime;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankAccountServiceJpa implements BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final BankAccountMapper bankAccountMapper;
    private final UserService userService;
    private final TransactionService transactionService;
    @Value("${deposit.maxIncrease}")
    private double maxIncrease;
    @Value("${deposit.everyMinuteIncrease}")
    private double everyMinuteIncrease;
    @Override
    @Transactional
    public BankAccountDto save(BankAccountDto accountDto) {
        Optional<User> optionalUser = userService.findEntityById(accountDto.getUserId());//
        if (!optionalUser.isPresent()) {
            throw new NoSuchElementException(String.format("User with id = %d not exist", accountDto.getUserId()));
        }
        User user = optionalUser.get();
        Optional<BankAccount> byUserId = bankAccountRepository.findByUser_Id(accountDto.getUserId());
        if (byUserId.isPresent()) {
            throw new NotUniqueUserException(String.format("User with id = %d already attached with account with id = %d",
                    accountDto.getUserId(),
                    byUserId.get().getId()
            ));
        }
        if (!ObjectUtils.isEmpty(bankAccountRepository.findByUsername(accountDto.getUsername()))) {
            throw new NotUniqueUsernameException(String.format("Account with username = %s already exist", accountDto.getUsername()));
        }
        if (accountDto.getDeposit() <= 0) {
            throw new ImpossibleBalanceException("Deposit cannot be less or equal 0");
        }

        accountDto.setBalance(accountDto.getDeposit());
        BankAccount bankAccount = bankAccountMapper.fromDtoToAccount(accountDto);
        bankAccount.setUser(user);
        BankAccount saved = bankAccountRepository.save(bankAccount);
        log.info("Create account with username {}",accountDto.getUsername());
        return bankAccountMapper.fromAccountToDto(saved);
    }

    @Override
    public BankAccountDto findById(Long id) {
        Optional<BankAccount> byId = bankAccountRepository.findById(id);
        if (byId.isEmpty()){
            throw new NoSuchElementException();
        }
        return bankAccountMapper.fromAccountToDto(byId.get());
    }

    @Override
    public BankAccountDto update(BankAccountDto bankAccountDto) {
        BankAccount bankAccount = bankAccountMapper.fromDtoToAccount(bankAccountDto);
        BankAccount updated = bankAccountRepository.save(bankAccount);
        log.info("Update account with username {}",bankAccountDto.getUsername());
        return bankAccountMapper.fromAccountToDto(updated);
    }
    private void increaseBalance(BankAccount account){
        if (account.getBalance() < account.getDeposit() * maxIncrease) {
            account.setBalance((long)((double) account.getBalance() * everyMinuteIncrease));
        }
    }

    @Override
    @Scheduled(fixedRate = 1000)
    public void everyMinuteIncrease() {
        int pageSize = 100;
        //
        long total = bankAccountRepository.count();
        for (int page = 0, totalPages = (int) Math.ceil((double)total / pageSize); page < totalPages; page++) {
            Page<BankAccount> all = bankAccountRepository.findAll(PageRequest.of(page, pageSize));
            List<BankAccount> accounts = all.getContent();
            accounts.forEach(a->increaseBalance(a));
            bankAccountRepository.saveAll(accounts);

        }
    }
    public void validateTransaction(Long receiverId, Long senderId, Long amount) {
        if (ObjectUtils.isEmpty(findById(receiverId))) {
            throw new NoSuchElementException(String.format("Account of receiver with id = %d not exist", receiverId));
        }
        if (ObjectUtils.isEmpty(findById(senderId))) {
            throw new NoSuchElementException(String.format("Account of sender with id = %d not exist", senderId));
        }
        if (findById(senderId).getBalance() - amount <= 0) {
            throw new ImpossibleBalanceException(String.format("Account with id = %d have not enough balance to create " +
                    "a transaction with amount = %d", receiverId, amount));
        }
    }
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public BankAccountDto transaction(Long receiverAccountId, Long senderAccountId, Long amount) {

        validateTransaction(receiverAccountId, senderAccountId, amount);

        BankAccountDto senderAccountDto = findById(senderAccountId);
        senderAccountDto.setBalance(senderAccountDto.getBalance() - amount);
        update(senderAccountDto);

        transactionService.createTransaction(TransactionDto.builder()
                .accountId(senderAccountId).timestamp(OffsetDateTime.now()).amount(-amount).build());

        BankAccountDto receiverAccountDto = findById(receiverAccountId);
        receiverAccountDto.setBalance(receiverAccountDto.getBalance() + amount);
        update(receiverAccountDto);

        transactionService.createTransaction(TransactionDto.builder()
                .accountId(receiverAccountId).timestamp(OffsetDateTime.now()).amount(amount).build());
        log.info("Transaction between users with id {} and {} created",receiverAccountId,senderAccountId);
        return senderAccountDto;
    }

}
