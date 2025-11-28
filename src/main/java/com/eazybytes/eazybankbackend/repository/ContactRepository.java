package com.eazybytes.eazybankbackend.repository;

import com.eazybytes.eazybankbackend.model.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends CrudRepository<Contact, String> {

    // CRUD standard sulla tabella contact_messages
}
