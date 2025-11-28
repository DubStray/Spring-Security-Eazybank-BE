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
@Table(name = "contact_messages")
public class Contact {

    // PK stringa generata lato app (prefisso SR)
    @Id
    @Column(name = "contact_id")
    private String contactId;

    // Nome di chi contatta
    @Column(name = "contact_name")
    private String contactName;

    // Email del mittente
    @Column(name = "contact_email")
    private String contactEmail;

    // Oggetto del messaggio
    private String subject;

    // Testo del messaggio
    private String message;

    // Data di creazione record
    @Column(name = "create_dt")
    private Date createDt;

}
