package com.eazybytes.eazybankbackend.repository;

import com.eazybytes.eazybankbackend.model.Accounts;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepository extends CrudRepository<Accounts, Long> {

    // Query derivata per recuperare un account dato l'id cliente
    Accounts findByCustomerId(long customerId);

}
