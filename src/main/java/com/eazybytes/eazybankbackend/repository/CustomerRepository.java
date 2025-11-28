package com.eazybytes.eazybankbackend.repository;

import com.eazybytes.eazybankbackend.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    // Trova un cliente per email (usata come username)
    Optional<Customer> findByEmail(String email);
}
