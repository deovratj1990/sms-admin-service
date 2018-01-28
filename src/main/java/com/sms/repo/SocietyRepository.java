package com.sms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.sms.domain.Society;

public interface SocietyRepository extends CrudRepository<Society, Integer>, SocietyRepositoryCustom {
	public Society findBySocietyNameAndLocalityId(String societyName, Integer localityName);
	
	@Query("select s, l, p, sub"
		+ " from Society s, Locality l, Pincode p, Subscription sub"
		+ " where s.localityId = l.localityId"
		+ " and l.pincodeId = p.pincodeId"
		+ " and s.societyId = sub.societyId"
		+ " and sub.subscriptionEndDate = ("
			+ "select max(subIn.subscriptionEndDate)"
			+ " from Subscription subIn"
			+ " where subIn.societyId = s.societyId"
		+ ")")
	public List<Object[]> getAllSocietySubscription();
	
	@Query("select societyRoomCount, ("
			+ "select max(subscriptionEndDate)"
			+ " from Subscription"
			+ " where societyId = :societyId"
			+ " and ("
				+ "subscriptionStatus = com.sms.domain.constant.SubscriptionStatus.ACTIVE"
				+ " or subscriptionStatus = com.sms.domain.constant.SubscriptionStatus.FUTURE"
			+ ")"
		+ ") as subscriptionEndDate"
		+ " from Society"
		+ " where societyId = :societyId")
	public List<Object[]> getInfoForAddSubscription(@Param("societyId") Integer societyId);
}
