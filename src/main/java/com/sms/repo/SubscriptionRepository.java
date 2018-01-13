package com.sms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.sms.domain.Subscription;

public interface SubscriptionRepository extends CrudRepository<Subscription, Integer> {
	public List<Subscription> findBySocietyIdOrderBySubscriptionEndDateDesc(Integer societyId);
	
	@Query("select sub, sum(t.transactionAmount)"
		+ " from Subscription sub, Transaction t"
		+ " where sub.subscriptionId = :subscriptionId"
		+ " and t.subscriptionId = :subscriptionId"
		+ " and t.transactionStatus != com.sms.domain.constant.TransactionStatus.FAILED")
	public List<Object[]> getSubscriptionTransaction(@Param("subscriptionId") Integer subscriptionId);
}
