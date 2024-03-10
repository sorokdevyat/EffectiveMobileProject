package com.ppr.java.effectivemobileproject.repository;

import com.ppr.java.effectivemobileproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u JOIN u.phoneNumbers  WHERE :phoneNumber MEMBER OF u.phoneNumbers")
    boolean isPhoneRegistered(String phoneNumber);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u JOIN u.emails  WHERE :email MEMBER OF u.emails")
    boolean isEmailRegistered(String email);


}
