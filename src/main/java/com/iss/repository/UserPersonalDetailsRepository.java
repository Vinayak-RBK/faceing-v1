package com.iss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.iss.entity.CommonUserDetailsTable;
import com.iss.entity.UsersPersonalDetails;

@Repository
public interface UserPersonalDetailsRepository extends JpaRepository<UsersPersonalDetails, String> {

//	String query = "INSERT INTO `faceing`.`users_personal_details`\r\n"
//			+ " (`user_personal_detail_id`, `last_update_date`, `last_update_pname`, `regist_date`, `regist_pname`, \r\n"
//			+ " `user_current_health_cond`, `user_current_medication`, `user_dob`, `user_drink_alcohol`, `user_email_id`, \r\n"
//			+ " `user_gender`, `user_image`, `user_name`,`user_height`,`user_weight`) VALUES\r\n"
//			+ " (?1, ?2, ?3, ?4,?5,?6,?7,?8,?9,?10,?11,?12,?13,?14,?15);";
//
//	@Modifying
//	@Transactional
//	@Query(value = query, nativeQuery = true)
//	public int insertUserPersonalDetails(String userPerDetailId, Date lastUpdateDate, String lastUpdatePName,
//			Date registDate, String registPname, String uerCurHealthCond, String userCrMed, Date dob,
//			String userDrinkAlcohol, String emailId, String gender, String userImagePath, String userName,
//			double height, double weight);
	
	@Query(value = "Select userPerd.user_id,userPerd.user_personal_detail_id,userPerd.user_name,userPerd.user_email_id,userPerd.user_gender,\r\n"
			+ "userPerd.user_height,userPerd.user_image,userPerd.user_dob,userPerd.user_weight, userd.is_blocked\r\n"
			+ "from users_personal_details userPerd\r\n"
			+ " inner join end_user userd on userPerd.user_id=userd.user_id \r\n"
			+ " where userPerd.user_id=:userId", nativeQuery = true)
	public CommonUserDetailsTable findUserPersonalDetailsByUserId(Long userId);
	
	@Query(value = " SELECT * FROM users_personal_details where user_id=:userId", nativeQuery = true)
	public UsersPersonalDetails getByUserId(Long userId);

}
