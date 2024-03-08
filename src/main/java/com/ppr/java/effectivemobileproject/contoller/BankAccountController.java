package com.ppr.java.effectivemobileproject.contoller;

import com.ppr.java.effectivemobileproject.dto.account.BankAccountDto;
import com.ppr.java.effectivemobileproject.dto.transaction.TransactionDto;
import com.ppr.java.effectivemobileproject.service.BankAccountService;
import com.ppr.java.effectivemobileproject.service.TransactionService;
import com.ppr.java.effectivemobileproject.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/emproject/accounts")
@Tag(name = "Account",description = "Account Controller")
public class BankAccountController {
    private final BankAccountService bankAccountService;
    private final TransactionService transactionService;
    @GetMapping("/getAccountTransaction/{accountId}")
    public List<TransactionDto> getAllAccountTransactions(@PathVariable Long accountId){
        return transactionService.getAllAccountTransactions(accountId);
    }
    @PutMapping("/createTransaction")
    public BankAccountDto createTransaction(@RequestParam Long receiverId, @RequestParam Long senderId, @RequestParam Long amount){
        return bankAccountService.transaction(receiverId,senderId,amount);
    }
    @PostMapping("/createAccount")
    public BankAccountDto create(@RequestBody BankAccountDto bankAccountDto){
        return bankAccountService.save(bankAccountDto);
    }
}
