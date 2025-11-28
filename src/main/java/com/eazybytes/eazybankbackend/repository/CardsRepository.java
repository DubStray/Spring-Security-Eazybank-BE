package com.eazybytes.eazybankbackend.repository;

import com.eazybytes.eazybankbackend.model.Cards;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardsRepository extends CrudRepository<Cards, Long> {

    // Restituisce tutte le carte di un cliente
    List<Cards> findByCustomerId(long customerId);

}
