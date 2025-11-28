package com.eazybytes.eazybankbackend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter @Setter
public class Accounts {

    // Colonna di join verso customer (non chiave primaria qui)
    @Column(name = "customer_id")
    private long customerId;

    // Primary key dell'account
    @Id
    @Column(name="account_number")
    private long accountNumber;

    // Tipologia di conto (es. Savings)
    @Column(name="account_type")
    private String accountType;

    // Indirizzo della filiale
    @Column(name = "branch_address")
    private String branchAddress;

    // Data di creazione record
    @Column(name = "create_dt")
    private Date createDt;

}
