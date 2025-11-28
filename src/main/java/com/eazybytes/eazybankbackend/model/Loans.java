package com.eazybytes.eazybankbackend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "loans")
public class Loans {

    // PK del prestito
    @Id
    @Column(name = "loan_number")
    private long loanNumber;

    // Cliente a cui appartiene il prestito
    @Column(name = "customer_id")
    private long customerId;

    // Data di inizio prestito
    @Column(name = "start_dt")
    private Date startDt;

    // Tipologia di prestito (Home, Vehicle, ecc.)
    @Column(name = "loan_type")
    private String loanType;

    // Importo totale erogato
    @Column(name = "total_loan")
    private int totalLoan;

    // Importo già rimborsato
    @Column(name = "amount_paid")
    private int amountPaid;

    // Importo residuo
    @Column(name = "outstanding_amount")
    private int outstandingAmount;

    // Data creazione record
    @Column(name = "create_dt")
    private Date createDt;

}
