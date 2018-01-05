package com.sms.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sms.domain.Society;

public interface SocietyRepository extends CrudRepository<Society, Integer>, SocietyRepositoryCustom {
	public Society findBySocietyNameAndLocalityId(String societyName, Integer localityName);
	
	@Query(value = "select s.society_id, sp.subscription_period_id, s.society_name, l.locality_name, p.pincode_name, sp.subscription_period_start_date, sp.subscription_period_end_date,"
			+ " sp.subscription_period_type, sp.subscription_period_status"
			+ " from society s"
			+ " inner join locality l on s.locality_id = l.locality_id"
			+ " inner join pincode p on l.pincode_id = p.pincode_id"
			+ " inner join subscription_period sp on s.society_id = sp.society_id"
			+ " where sp.subscription_period_end_date = ("
				+ "select max(sp_in.subscription_period_end_date)"
				+ " from subscription_period sp_in"
				+ " where sp_in.society_id = s.society_id"
			+ ")", nativeQuery = true)
	public List<Object[]> getAllSocietySubscription();
}
