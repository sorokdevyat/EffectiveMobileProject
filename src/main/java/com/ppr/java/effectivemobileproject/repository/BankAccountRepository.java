package com.ppr.java.effectivemobileproject.repository;

import com.ppr.java.effectivemobileproject.model.BankAccount;
import lombok.extern.java.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount,Long> {
    Optional<BankAccount> findByUsername(String username);
    Optional<BankAccount> findByUser_Id(Long id);
}
