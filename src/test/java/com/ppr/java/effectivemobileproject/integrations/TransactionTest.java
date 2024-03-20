package com.ppr.java.effectivemobileproject.integrations;

import com.ppr.java.effectivemobileproject.EffectiveMobileProjectApplication;
import com.ppr.java.effectivemobileproject.dto.account.BankAccountDto;
import com.ppr.java.effectivemobileproject.dto.transaction.TransactionDto;
import com.ppr.java.effectivemobileproject.dto.user.UserDto;
import com.ppr.java.effectivemobileproject.model.BankAccount;
import com.ppr.java.effectivemobileproject.model.User;
import com.ppr.java.effectivemobileproject.model.exceptions.ImpossibleBalanceException;
import com.ppr.java.effectivemobileproject.repository.BankAccountRepository;
import com.ppr.java.effectivemobileproject.repository.UserRepository;
import com.ppr.java.effectivemobileproject.service.BankAccountService;
import com.ppr.java.effectivemobileproject.service.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.*;

@SpringBootTest(classes = EffectiveMobileProjectApplication.class)
public class TransactionTest extends IntegrationEnvironment {
    // Для аккаунтов созданы юзеры
    // receiver и sender сущемтвуют
    // достаточно баланса
    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionService transactionService;

    @Test
    void transaction_ReceiverWithSuchIdNotExist_ThrowException(){
        Assertions.assertThrows(NoSuchElementException.class,()->bankAccountService.transaction(1l,2l,1l));
    }
    @Test
    void transaction_SenderWithSuchIdNotExist_ThrowException() {
        User user = createUser();
        BankAccount receiverAccount = createAccount(500l, user);
        userRepository.save(user);
        bankAccountRepository.save(receiverAccount);
        Assertions.assertThrows(NoSuchElementException.class,()->bankAccountService.transaction(receiverAccount.getId(), 2l,200l));
    }
    @Test
    void transaction_NotEnoughBalance_ThrowImpossibleBalanceException(){
        User user1 = createUser();
        BankAccount receiverAccount = createAccount(500l, user1);
        userRepository.save(user1);
        bankAccountRepository.save(receiverAccount);

        User user2 = createUser();
        BankAccount senderAccount = createAccount(500l, user2);
        userRepository.save(user2);
        bankAccountRepository.save(senderAccount);

        Assertions.assertThrows(ImpossibleBalanceException.class,()->bankAccountService.transaction(receiverAccount.getId(), senderAccount.getId(), 1000l));
    }
    @Test
    void transaction_EnoughBalanceAnd_SenderBalanceUpdated(){
        User user1 = createUser();
        userRepository.save(user1);
        BankAccount receiverAccount = createAccount(500l, user1);
        bankAccountRepository.save(receiverAccount);

        User user2 = createUser();
        userRepository.save(user2);
        BankAccount senderAccount = createAccount(500l, user2);
        bankAccountRepository.save(senderAccount);

        bankAccountService.transaction(receiverAccount.getId(), senderAccount.getId(), 300l);

        BankAccount updatedBankAccount1 = bankAccountRepository.findById(senderAccount.getId()).get();
        BankAccount updatedBankAccount2 = bankAccountRepository.findById(receiverAccount.getId()).get();

        List<TransactionDto> allAccountTransactions1 = transactionService.getAllAccountTransactions(receiverAccount.getId());
        List<TransactionDto> allAccountTransactions2 = transactionService.getAllAccountTransactions(senderAccount.getId());

        Assertions.assertEquals(200l,updatedBankAccount1.getBalance());
        Assertions.assertEquals(800l,updatedBankAccount2.getBalance());

        Assertions.assertTrue(allAccountTransactions1.stream().filter(t->t.getAmount()==300l && t.getAccountId().equals(receiverAccount.getId())).findFirst().isPresent());
        Assertions.assertTrue(allAccountTransactions2.stream().filter(t->t.getAmount()==-300l && t.getAccountId().equals(senderAccount.getId())).findFirst().isPresent());
    }


    protected Long getRandomLong() {
        return UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }
    protected User createUser() {
        Set<String> phones = new HashSet<>();
        phones.add("84846854654564");

        Set<String> emails = new HashSet<>();
        emails.add(UUID.randomUUID().toString());


        return User.builder()
                .fullname(UUID.randomUUID().toString())
                .phoneNumbers(phones)
                .emails(emails)
                .dateOfBirth(LocalDate.now())
                .build();
    }
    protected BankAccount createAccount(Long balance,User user) {
        return BankAccount.builder()
                .deposit(balance)
                .balance(balance)
                .username(UUID.randomUUID().toString())
                .password(UUID.randomUUID().toString())
                .user(user)
                .build();
    }
}
