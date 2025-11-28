package com.eazybytes.eazybankbackend.repository;

import com.eazybytes.eazybankbackend.model.AccountTransactions;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountTransactionsRepository extends CrudRepository<AccountTransactions, String> {

    // Restituisce le transazioni di un cliente ordinate per data decrescente
    List<AccountTransactions> findByCustomerIdOrderByTransactionDtDesc(long customerId);
}
