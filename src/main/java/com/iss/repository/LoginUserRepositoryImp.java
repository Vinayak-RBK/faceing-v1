package com.iss.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.iss.dao.HealthRequestDao;
import com.iss.dao.PagerRequestDao;
import com.iss.dao.UserHealthDetailsResponseDao;
import com.iss.dao.UserHealthRequestDao;
import com.iss.entity.CommonUserDetailsTable;
import com.iss.entity.EndUser;
import com.iss.entity.Guest;
import com.iss.properties.FieldNames;
import com.iss.util.DateFormatUtil;
import com.iss.util.EncryptedDecryptedObjectUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Repository
public class LoginUserRepositoryImp {

	@Value("${SECRET_KEY}")
	private String secretKey;

	@Value("${SECRET_IV}")
	private String secretIv;

	@Value("${IS_ENCRYPT_DECRYPT_ENABLE_DATABASE_DATA}")
	private boolean isEncryptDecryptDatabaseData;

	@Autowired
	private EntityManager em;

	@Autowired
	private LoginUserRespository loginUserRespository;

	public int getCountOfUsers(String isOnboarded, PagerRequestDao dao) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT COUNT(*) FROM faceing.end_user eu ");
		sb.append(" INNER JOIN faceing.users_personal_details up ON eu.user_id = up.user_id ");
		sb.append("WHERE eu.is_onboarded = :isOnboarded");

		if (dao.getSearchBy() != null && !dao.getSearchBy().isEmpty()) {
			sb.append(" AND up.user_name like :userName or up.user_email_id like :userName ");
		}

		Query query = em.createNativeQuery(sb.toString());
		query.setParameter("isOnboarded", isOnboarded);

		if (dao.getSearchBy() != null && !dao.getSearchBy().isEmpty()) {
			query.setParameter("userName", "%" + dao.getSearchBy() + "%");
		}

		Long result = (Long) query.getSingleResult();

		System.out.println("Count is : " + result);

		return result.intValue();
	}

	@SuppressWarnings("unchecked")
	public List<CommonUserDetailsTable> getPersonalDetailsOfUsers(String isOnboarded, PagerRequestDao dao, int pageSize,
			int offSetvalue) {

		List<CommonUserDetailsTable> dtoList = new ArrayList<>();
		CommonUserDetailsTable respDao = new CommonUserDetailsTable();

		StringBuilder sb = new StringBuilder();
		sb.append(
				"SELECT userp.user_name, userp.user_gender, userp.user_weight, userp.user_dob, userp.user_email_id, euser.user_id,");
		sb.append(
				" userp.user_height, userp.user_image, userp.user_personal_detail_id,euser.is_blocked ,euser.sdk_type, euser.user_password, euser.job_role ");
		sb.append(" FROM users_personal_details userp INNER JOIN end_user euser ON userp.user_id = euser.user_id ");

		if (dao.getSearchBy() != null && !dao.getSearchBy().isEmpty()) {
			sb.append(" where userp.user_name like :userName or userp.user_email_id like :userName");
		}

		if (!dao.getSort().getSortBy().isEmpty()) {
			sb.append(" order by   ");
			sb.append(dao.getSort().getSortBy());
			sb.append("   ");
			sb.append(dao.getSort().getSortType());
		} else {
			sb.append(" order by userp.regist_date DESC ");
		}

		sb.append(" limit :size OFFSET :offSetValue ");

		Query query = em.createNativeQuery(sb.toString());

		if (dao.getSearchBy() != null && !dao.getSearchBy().isEmpty()) {
			query.setParameter("userName", "%" + dao.getSearchBy() + "%");
		}

		query.setParameter("size", pageSize);
		query.setParameter("offSetValue", offSetvalue);

		List<Object[]> rs = query.getResultList();
		for (Object[] row : rs) {
			respDao = new CommonUserDetailsTable();

			respDao.setUserName((String) row[0]);
			respDao.setUserGender((String) row[1]);
			respDao.setUserWeight((String) row[2]);
			respDao.setUserDOB((String) row[3]);
			respDao.setUserEmail((String) row[4]);
			respDao.setUserId(((Long) row[5]).toString());
			respDao.setUserHeight((String) row[6]);
			respDao.setUserImage((String) row[7]);
			dtoList.add(respDao);
		}

		return dtoList;

	}

	public int getCountOfGuests(String isDelete, PagerRequestDao dao) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT COUNT(*) FROM faceing.guests gu ");
		sb.append("LEFT JOIN faceing.end_user eu ON gu.user_id = eu.user_id ");
		sb.append("WHERE gu.user_id =:userId ");
		sb.append(" AND gu.is_delete = :isDelete");

		if (dao.getSearchBy() != null && !dao.getSearchBy().isEmpty()) {
			sb.append(" AND gu.guest_name like :guestName  ");
		}

		Query query = em.createNativeQuery(sb.toString());
		query.setParameter("userId", dao.getUserId());
		query.setParameter("isDelete", isDelete);

		if (dao.getSearchBy() != null && !dao.getSearchBy().isEmpty()) {
			query.setParameter("guestName", "%" + dao.getSearchBy() + "%");
		}

		Long result = (Long) query.getSingleResult();

		System.out.println("Count is : " + result);

		return result.intValue();
	}

	@SuppressWarnings("unchecked")
	public List<Guest> getPersonalDetailsOfGuest(String isDelete, PagerRequestDao dao, int pageSize, int offSetvalue) {

		List<Guest> dtoList = new ArrayList<>();
		Guest respDao = new Guest();

		StringBuilder sb = new StringBuilder();
		sb.append(
				"SELECT guests.guest_name, guests.guest_dob, guests.guest_gender,guests.guest_height,guests.guest_weight,guests.regist_date,guests.guest_id "
						+ " FROM guests LEFT JOIN end_user  ON guests.user_id = end_user.user_id\r\n"
						+ "WHERE guests.user_id= :userId AND guests.is_delete=:isDelete ");

		if (dao.getSearchBy() != null && !dao.getSearchBy().isEmpty()) {
			sb.append(" AND guests.guest_name like :guestName ");
		}

		if (!dao.getSort().getSortBy().isEmpty()) {
			sb.append(" order by   ");
			sb.append(dao.getSort().getSortBy());
			sb.append("   ");
			sb.append(dao.getSort().getSortType());
		} else {
			sb.append(" order by guests.regist_date DESC ");
		}

		sb.append(" limit :size OFFSET :offSetValue ");

		Query query = em.createNativeQuery(sb.toString());

		query.setParameter("userId", dao.getUserId());
		query.setParameter("isDelete", isDelete);

		if (dao.getSearchBy() != null && !dao.getSearchBy().isEmpty()) {
			query.setParameter("guestName", "%" + dao.getSearchBy() + "%");
		}

		query.setParameter("size", pageSize);
		query.setParameter("offSetValue", offSetvalue);

		List<Object[]> rs = query.getResultList();
		for (Object[] row : rs) {
			respDao = new Guest();

			respDao.setGuestName((String) row[0]);
			respDao.setGuestDOB((String) row[1]);
			respDao.setGuestGender((String) row[2]);
			respDao.setGuestHeight((String) row[3]);
			respDao.setGuestWeight((String) row[4]);
			respDao.setRegistDate((String) row[5]);
			respDao.setGuestId((Long) row[6]);
			dtoList.add(respDao);
		}

		return dtoList;

	}

	public UserHealthDetailsResponseDao getUserHealthHistoryList(UserHealthRequestDao reqDao) {
		UserHealthDetailsResponseDao respDao = new UserHealthDetailsResponseDao();

		List<HealthRequestDao> values = new ArrayList<HealthRequestDao>();
		String sdkType = "";

		HealthRequestDao healthRequestDao = new HealthRequestDao();

		try {
			Optional<EndUser> user = loginUserRespository.findById(reqDao.getUserId());

			if (user.isEmpty()) {
				respDao.setMsg("Wrong user Id is recieved");
			}

			sdkType = (String) EncryptedDecryptedObjectUtil.getDecryptedObject(user.get().getsDKType(), secretKey,
					secretIv, isEncryptDecryptDatabaseData);

			StringBuilder sb = new StringBuilder();
			sb.append("Select regist_date,");
			sb.append(reqDao.getFieldName());

			if (FieldNames.SDK_ANURA.equals(sdkType)) {
				sb.append(" from user_health_anura_detail ");
			} else if (FieldNames.SDK_ANURA.equals(sdkType)) {
				sb.append(" from user_health_binah_detail ");
			}

			sb.append("where user_id=:userId ");

			if (!reqDao.getStartDate().isEmpty() && reqDao.getStartDate() != null) {
				sb.append(" AND regist_date >= '");
				sb.append(DateFormatUtil.getFormatedDateToStringFormat1(reqDao.getStartDate()).trim());
				sb.append("'");
			}

			if (!reqDao.getEndDate().isEmpty() && reqDao.getEndDate() != null) {
				sb.append(" AND regist_date <= '");
				sb.append(DateFormatUtil.getFormatedDateToStringFormat1(reqDao.getEndDate()).trim());
				sb.append("'");
			}

			sb.append(" order by regist_date DESC");

			Query query = em.createNativeQuery(sb.toString());

			query.setParameter("userId", reqDao.getUserId());

			@SuppressWarnings("unchecked")
			List<Object[]> rs = query.getResultList();

			for (Object[] row : rs) {

				healthRequestDao = new HealthRequestDao();
				
				healthRequestDao.setScannedDate((String) row[0]);
				healthRequestDao.setValue((String) row[1]);

				values.add(healthRequestDao);
			}

			respDao.setValues(values);
			respDao.setSuccess(Boolean.toString(true));

			return respDao;
		} catch (Exception e) {
			System.out.println(e);
			respDao.setSuccess(Boolean.toString(false));
		}
		return respDao;

	}

}
