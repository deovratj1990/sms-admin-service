package com.sms.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sms.domain.Transaction;
import com.sms.domain.constant.TransactionStatus;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
	public List<Transaction> findBySubscriptionIdAndTransactionStatusIn(Integer subscriptionId, List<TransactionStatus> transactionStatusList);
}
