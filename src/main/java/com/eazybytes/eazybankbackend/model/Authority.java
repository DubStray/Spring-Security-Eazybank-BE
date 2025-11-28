package com.eazybytes.eazybankbackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name="authorities")
public class Authority {

    // PK autoincrement
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Nome del permesso/ruolo (es. ROLE_USER)
    private String name;

    // Relazione molti-a-uno con Customer
    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

}
