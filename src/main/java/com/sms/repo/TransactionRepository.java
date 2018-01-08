package com.sms.repo;

import org.springframework.data.repository.CrudRepository;

import com.sms.domain.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {

}
