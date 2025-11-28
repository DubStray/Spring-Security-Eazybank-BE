package com.eazybytes.eazybankbackend.repository;

import com.eazybytes.eazybankbackend.model.Loans;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends CrudRepository<Loans, Long> {

    // Restituisce i prestiti di un cliente, ordinati per data di inizio decrescente
    List<Loans> findByCustomerIdOrderByStartDtDesc(long customerId);

}
