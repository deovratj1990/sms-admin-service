package com.sms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.sms.domain.Subscription;

public interface SubscriptionRepository extends CrudRepository<Subscription, Integer> {
	@Query(value = "select sub.subscription_id, sub.society_id, sub.subscription_start_date, sub.subscription_end_date, sub.subscription_type, sub.subscription_status, sub.subscription_payment_status, sub.subscription_amount, sum(t.transaction_amount) as paid_amount"
		+ " from subscription sub"
		+ " left join transaction t"
		+ " on sub.subscription_id = t.subscription_id"
		+ " and ("
			+ "t.transaction_status = :tStatus1"
			+ " or t.transaction_status = :tStatus2"
		+ ")"
		+ " where sub.society_id = :societyId"
		+ " and sub.subscription_status = :subStatusActive"
		+ " or sub.subscription_status = :subStatusFuture"
		+ " order by sub.subscription_end_date desc", nativeQuery = true)
	public List<Object[]> getSubscriptionTransactionBySocietyIdOrderBySubscriptionEndDateDesc(@Param("societyId") Integer societyId, @Param("subStatusActive") Integer subStatusActive, @Param("subStatusFuture") Integer subStatusFuture, @Param("tStatus1") Integer tStatus1, @Param("tStatus2") Integer tStatus2);
	
	@Query("select sub, sum(t.transactionAmount)"
		+ " from Subscription sub, Transaction t"
		+ " where sub.subscriptionId = :subscriptionId"
		+ " and t.subscriptionId = :subscriptionId"
		+ " and ("
			+ "sub.subscriptionStatus = com.sms.domain.constant.SubscriptionStatus.ACTIVE"
			+ " or sub.subscriptionStatus = com.sms.domain.constant.SubscriptionStatus.FUTURE"
		+ ")"
		+ " and ("
			+ "t.transactionStatus = com.sms.domain.constant.TransactionStatus.PENDING"
			+ " or t.transactionStatus = com.sms.domain.constant.TransactionStatus.SUCCESSFUL"
		+ ")")
	public List<Object[]> getSubscriptionForTransaction(@Param("subscriptionId") Integer subscriptionId);
	
	@Query("select sub.subscriptionAmount, sum(t.transactionAmount) as transactionAmount"
		+ " from Subscription sub, Transaction t"
		+ " where sub.subscriptionId = :subscriptionId"
		+ " and ("
			+ "t.transactionStatus = com.sms.domain.constant.TransactionStatus.SUCCESSFUL"
			+ " or t.transactionStatus = com.sms.domain.constant.TransactionStatus.PENDING"
		+ ")")
	public List<Object[]> getSubscriptionAmountInfo(@Param("subscriptionId") Integer subscriptionId);
}
