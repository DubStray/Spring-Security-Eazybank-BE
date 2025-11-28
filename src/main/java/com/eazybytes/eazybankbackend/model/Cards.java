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
@Table(name = "cards")
public class Cards {

    // PK della carta
    @Id
    @Column(name = "card_id")
    private long cardId;

    // Cliente proprietario
    @Column(name = "customer_id")
    private long customerId;

    // Numero carta (mascherato nel seed)
    @Column(name = "card_number")
    private String cardNumber;

    // Tipo carta (es. Credit)
    @Column(name = "card_type")
    private String cardType;

    // Plafond totale
    @Column(name = "total_limit")
    private int totalLimit;

    // Importo utilizzato
    @Column(name = "amount_used")
    private int amountUsed;

    // Importo residuo disponibile
    @Column(name = "available_amount")
    private int availableAmount;

    // Data creazione record
    @Column(name = "create_dt")
    private Date createDt;

}
