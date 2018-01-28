package com.sms.repo;

import java.util.List;

public interface SubscriptionRepositoryCustom {
	public List<Object[]> getSubscriptionTransactionBySocietyIdOrderBySubscriptionEndDateDesc(Integer societyId);
}
