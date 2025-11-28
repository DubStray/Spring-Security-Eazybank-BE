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
@Table(name="account_transactions")
public class AccountTransactions {

    // PK della transazione (UUID nel seed)
    @Id
    @Column(name = "transaction_id")
    private String transactionId;

    // Numero di conto a cui appartiene la transazione
    @Column(name="account_number")
    private long accountNumber;

    // Cliente proprietario del conto
    @Column(name = "customer_id")
    private long customerId;

    // Data di esecuzione della transazione
    @Column(name="transaction_dt")
    private Date transactionDt;

    // Descrizione sintetica
    @Column(name = "transaction_summary")
    private String transactionSummary;

    // Tipo (es. Withdrawal/Deposit)
    @Column(name="transaction_type")
    private String transactionType;

    // Importo movimentato
    @Column(name = "transaction_amt")
    private int transactionAmt;

    // Saldo dopo la transazione
    @Column(name = "closing_balance")
    private int closingBalance;

    // Data di inserimento record
    @Column(name = "create_dt")
    private Date createDt;

}
