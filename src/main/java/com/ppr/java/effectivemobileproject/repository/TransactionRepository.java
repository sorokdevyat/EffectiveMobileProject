package com.ppr.java.effectivemobileproject.repository;

import com.ppr.java.effectivemobileproject.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findAllByAccount_Id(Long id);
}
