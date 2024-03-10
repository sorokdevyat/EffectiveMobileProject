package com.ppr.java.effectivemobileproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "account")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "balance")
    private Long balance;
    @Column(name = "deposit")
    private Long deposit;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
