package com.eazybytes.eazybankbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Entity
@Getter @Setter
public class Customer {

    // PK autoincrement della tabella customer
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private long id;

    // Nome cliente
    private String name;

    // Email, usata come username per il login
    private String email;

    // Numero di telefono
    @Column(name = "mobile_number")
    private String mobileNumber;

    // Password in chiaro in input, resa write-only in serializzazione JSON
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String pwd;

    // Ruolo applicativo (stringa semplice)
    private String role;

    // Data di creazione record (non esposta in JSON)
    @Column(name = "create_dt")
    @JsonIgnore
    private Date createDt;

    // Authorities (permessi/ruoli) caricati eager; nascosti da JSON
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Authority> authorities;

}
