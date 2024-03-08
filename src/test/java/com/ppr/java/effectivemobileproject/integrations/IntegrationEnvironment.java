package com.ppr.java.effectivemobileproject.integrations;


import com.ppr.java.effectivemobileproject.dto.account.BankAccountDto;
import com.ppr.java.effectivemobileproject.dto.user.UserDto;
import com.ppr.java.effectivemobileproject.model.BankAccount;
import com.ppr.java.effectivemobileproject.model.User;
import com.ppr.java.effectivemobileproject.repository.BankAccountRepository;
import com.ppr.java.effectivemobileproject.repository.TransactionRepository;
import com.ppr.java.effectivemobileproject.repository.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class IntegrationEnvironment {
    static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres");

    @Autowired
    UserRepository userRepository;
    @Autowired
    BankAccountRepository bankAccountRepository;
    @Autowired
    TransactionRepository transactionRepository;
    
    @BeforeAll
    public static void startContainer() {
        POSTGRESQL_CONTAINER.start();
    }

    @AfterAll
    public static void stopContainer() {
        POSTGRESQL_CONTAINER.stop();
    }

    @BeforeEach
    void clearDatabase() {
        userRepository.deleteAll();
        bankAccountRepository.deleteAll();
        transactionRepository.deleteAll();
        
    }

    @DynamicPropertySource
    public static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRESQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRESQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRESQL_CONTAINER::getPassword);
    }

    protected Long getRandomLong() {
        return UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }

    protected UserDto createUserDto() {
        Set<String> phones = new HashSet<>();
        phones.add(UUID.randomUUID().toString());

        Set<String> emails = new HashSet<>();
        emails.add(UUID.randomUUID().toString());


        return UserDto.builder()
                .fullname(UUID.randomUUID().toString())
                .phoneNumbers(phones)
                .emails(emails)
                .dateOfBirth(LocalDate.now())
                .build();
    }

    protected User createUser() {
        Set<String> phones = new HashSet<>();
        phones.add(UUID.randomUUID().toString());

        Set<String> emails = new HashSet<>();
        emails.add(UUID.randomUUID().toString());


        return User.builder()
                .fullname(UUID.randomUUID().toString())
                .phoneNumbers(phones)
                .emails(emails)
                .dateOfBirth(LocalDate.now())
                .build();
    }

    protected BankAccountDto createAccountDto() {
        return BankAccountDto.builder()
                .deposit(getRandomLong())
                .balance(getRandomLong())
                .username(UUID.randomUUID().toString())
                .password(UUID.randomUUID().toString())
                .build();
    }

    protected BankAccount createAccount(Long id) {
        return BankAccount.builder()
                .deposit(getRandomLong())
                .balance(getRandomLong())
                .username(UUID.randomUUID().toString())
                .password(UUID.randomUUID().toString())
                .build();
    }


}
