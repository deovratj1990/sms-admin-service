package com.sms.repo.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;

import com.sms.domain.Subscription;
import com.sms.domain.Transaction;
import com.sms.repo.SubscriptionRepositoryCustom;

public class SubscriptionRepository implements SubscriptionRepositoryCustom {
	
	@Autowired
	private EntityManager em;

	@Override
	public List<Object[]> getSubscriptionTransactionBySocietyIdOrderBySubscriptionEndDateDesc(Integer societyId) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		
		CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
		
		Root<Subscription> sub = cq.from(Subscription.class);
		
		Join<Subscription, Transaction> t = sub.join("t", JoinType.LEFT);
		
		t.on(
			cb.and(
				cb.equal(sub.get("societyId"), cb.parameter(Integer.class, "societyId")),
				cb.equal(sub.get("subscriptionId"), t.get("subscriptionId"))
			)
		);
		
		cq.select(sub.get("subscriptionId"));
		
		return em.createQuery(cq).setParameter("societyId", societyId).getResultList();
	}
	
}
