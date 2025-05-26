package com.iss.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.iss.entity.CommonTable;
import com.iss.entity.Guest;

@Repository
public interface GuestUserSearchRepository extends JpaRepository<Guest, String> {

	@Query(value = "SELECT \r\n" + "    g.guest_id,\r\n" + "    g.user_email_id,\r\n" + "    g.guest_name,\r\n"
			+ "    g.guest_gender,\r\n" + "    g.guest_dob,\r\n" + "    g.guest_weight,\r\n" + "    g.guest_height,\r\n"
			+ "    g.guest_image,\r\n" + "    g.guest_current_health_cond,\r\n" + "    g.guest_current_medication,\r\n"
			+ "    g.guest_drink_alcohol,\r\n" + "    g.user_id,\r\n" + "    g.regist_date,\r\n"
			+ "    g.last_update_date,\r\n" + "    g.regist_pname,\r\n" + "    g.last_update_pname,\r\n"
			+ "    gd.guest_respiration_rate,\r\n" + "    gd.guest_blood_pressure,\r\n" + "    gd.guest_heart_rate,\r\n"
			+ "    gd.guest_oxygen_saturation,\r\n" + "    gd.guest_hrv_data,\r\n" + "    gd.guest_stress_level,\r\n"
			+ "    gd.guest_relaxation_level,\r\n" + "    gd.guest_energy_level,\r\n"
			+ "    gd.guest_body_shape_index,\r\n" + "    gd.guest_body_mass_index,\r\n" + "    gd.guest_age,\r\n"
			+ "    gd.guest_hemoglobin_level,\r\n" + "    gd.guest_fitness_level\r\n" + "FROM guests g\r\n"
			+ "INNER JOIN guest_health_detail gd ON g.guest_id = gd.guest_id\r\n"
			+ "LEFT JOIN users u ON g.user_id = u.user_id\r\n" + "WHERE g.guest_name LIKE CONCAT('%', ?1 , '%') \r\n"
			+ "  AND g.user_email_id = ?2;\r\n" + "", nativeQuery = true)
	public List<CommonTable> findByGuestName(String guestName, String userEmail);

	
	@Query(value = "SELECT guests.guest_name, guests.guest_dob, guests.guest_gender,guests.guest_height,guests.guest_weight,guests.guest_id,guests.guest_image,guests.user_email_id,\r\n"
			+ " guests.user_id,guests.last_update_date,guests.last_update_pname,guests.regist_date,guests.regist_pname\r\n"
			+ "FROM guests LEFT JOIN end_user  ON guests.user_id = end_user.user_id\r\n"
			+ "WHERE end_user.user_id= :userId and guests.is_delete=:isDelete" , nativeQuery = true)
	
	Optional<List<Guest>> findByUserId(String userId, boolean isDelete);

}
