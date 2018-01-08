package com.sms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sms.domain.Society;

public interface SocietyRepository extends CrudRepository<Society, Integer>, SocietyRepositoryCustom {
	public Society findBySocietyNameAndLocalityId(String societyName, Integer localityName);
	
	@Query(value = "select s.society_id, sub.subscription_id, s.society_name, l.locality_name, p.pincode_name, sub.subscription_start_date, sub.subscription_end_date,"
			+ " sub.subscription_type, sub.subscription_status"
			+ " from society s"
			+ " inner join locality l on s.locality_id = l.locality_id"
			+ " inner join pincode p on l.pincode_id = p.pincode_id"
			+ " inner join subscription sub on s.society_id = sub.society_id"
			+ " where sub.subscription_end_date = ("
				+ "select max(sub_in.subscription_end_date)"
				+ " from subscription sub_in"
				+ " where sub_in.society_id = s.society_id"
			+ ")", nativeQuery = true)
	public List<Object[]> getAllSocietySubscription();
}
