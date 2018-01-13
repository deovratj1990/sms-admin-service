package com.sms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

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
}
