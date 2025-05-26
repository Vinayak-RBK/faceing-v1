package com.iss.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.iss.dao.GuestHealthInfoDao;
import com.iss.dao.Response;
import com.iss.entity.EndUser;
import com.iss.entity.Guest;
import com.iss.entity.GuestHealthAnuraDetail;
import com.iss.entity.GuestHealthBinahDetail;
import com.iss.properties.FieldNames;
import com.iss.repository.GuestHealthAnuraDetailRepository;
import com.iss.repository.GuestHealthBinahDetailRepository;
import com.iss.repository.GuestRepository;
import com.iss.repository.GuestUserSearchRepository;
import com.iss.repository.LoginUserRespository;
import com.iss.util.EncryptedDecryptedObjectUtil;

import jakarta.transaction.Transactional;

@Service
public class GuestServiceImpl implements GuestService {
	@Value("${SOMETHING_WENT_WRONG}")
	private String someThingWentWrong;

	@Value("${GUEST_NOT_EXISTS}")
	private String guestNotExists;

	@Value("${UNABLE_REMOVE_GUEST}")
	private String unableToRemoveGuest;

	@Value("${REMOVE_GUEST_SUCCESS}")
	private String removeGuestSuccess;

	@Value("${ADD_GUEST_FAIL}")
	private String addGuestFail;

	@Value("${ADD_GUEST_SUCCESS}")
	private String addGuestSuccess;

	@Value("${SECRET_KEY}")
	private String secretKey;

	@Value("${SECRET_IV}")
	private String secretIv;

	@Value("${IS_ENCRYPT_DECRYPT_ENABLE_DATABASE_DATA}")
	private boolean isEncryptDecryptDatabaseData;

	@Autowired
	private GuestRepository guestRepository;

	@Autowired
	private GuestUserSearchRepository guestUserSearchRepository;

	@Autowired
	private GuestHealthAnuraDetailRepository guestHealthAnuraDetailRepository;

	@Autowired
	private GuestHealthBinahDetailRepository guestHealthBinahDetailRepository;
	@Autowired
	private LoginUserRespository loginUserRespository;

	@Transactional
	@Override
	public Response addGuest(GuestHealthInfoDao guestInfoDao, String dateTimeFormat) {
		Guest guest = new Guest();
		Guest enGuest = new Guest();
		Response resp = new Response();
		GuestHealthAnuraDetail guestHealthAnuraEnt = new GuestHealthAnuraDetail();
		GuestHealthAnuraDetail enGuestHealthAnuraEnt = new GuestHealthAnuraDetail();

		GuestHealthBinahDetail guestHealthBinahEnt = new GuestHealthBinahDetail();
		GuestHealthBinahDetail enGuestHealthBinahEnt = new GuestHealthBinahDetail();

		ModelMapper mapper = new ModelMapper();
		try {
			Optional<EndUser> user = loginUserRespository.findById(guestInfoDao.getGuestDao().getUserId().toString());

			if (user.isEmpty()) {
				resp.setMsg("Wrong User ID credential is received");
				resp.setSuccess(Boolean.toString(false));
				return resp;
			}

			SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
			String currentDate = sdf.format(new Date());

			guest.setGuestDOB(guestInfoDao.getGuestDao().getDob());
			guest.setGuestGender(guestInfoDao.getGuestDao().getGender());
			guest.setGuestHeight(guestInfoDao.getGuestDao().getHeight());
			guest.setGuestWeight(guestInfoDao.getGuestDao().getWeight());
			guest.setGuestName(guestInfoDao.getGuestDao().getName());
			guest.setIsdelete(Boolean.toString(false));
			guest.setRegistDate(currentDate);
			guest.setRegistPName(this.getClass().getSimpleName());
			guest.setLastUpdateDate(currentDate);
			guest.setLastUpdatePName(this.getClass().getSimpleName());

			enGuest = (Guest) EncryptedDecryptedObjectUtil.getEncryptedObject(guest, secretKey, secretIv,
					isEncryptDecryptDatabaseData);
			guest.setUser(user.get());

			Guest guestEnt = guestRepository.save(enGuest);

			if (guestEnt != null) {

				String decSdkType = (String) EncryptedDecryptedObjectUtil.getDecryptedString(user.get().getsDKType(),
						secretKey, secretIv, isEncryptDecryptDatabaseData);

				if (FieldNames.SDK_ANURA.equals(decSdkType)) {

					guestHealthAnuraEnt = mapper.map(guestInfoDao.getAnuraDetails(), GuestHealthAnuraDetail.class);

					guestHealthAnuraEnt.setRegistDate(currentDate);
					guestHealthAnuraEnt.setRegistPName(this.getClass().getSimpleName());
					guestHealthAnuraEnt.setLastUpdateDate(currentDate);
					guestHealthAnuraEnt.setLastUpdatePName(this.getClass().getSimpleName());

					enGuestHealthAnuraEnt = (GuestHealthAnuraDetail) EncryptedDecryptedObjectUtil
							.getEncryptedObject(guestHealthAnuraEnt, secretKey, secretIv, isEncryptDecryptDatabaseData);

					enGuestHealthAnuraEnt.setUserEmail(user.get().getUserEmail());
					enGuestHealthAnuraEnt.setGuest(guestEnt);

					GuestHealthAnuraDetail guestHealthAnuraDetail = guestHealthAnuraDetailRepository
							.save(enGuestHealthAnuraEnt);

					if (guestHealthAnuraDetail != null) {
						resp.setMsg(addGuestSuccess);
						resp.setSuccess(Boolean.toString(true));
						return resp;
					} else {

						resp.setMsg(addGuestFail);
						resp.setSuccess(Boolean.toString(false));
						return resp;
					}
				}

				else if (FieldNames.SDK_BINAH.equals(decSdkType)) {
					
					guestHealthBinahEnt = mapper.map(guestInfoDao.getBinahDetails(), GuestHealthBinahDetail.class);

					guestHealthBinahEnt.setRegistDate(currentDate);
					guestHealthBinahEnt.setRegistPName(this.getClass().getSimpleName());
					guestHealthBinahEnt.setLastUpdateDate(currentDate);
					guestHealthBinahEnt.setLastUpdatePName(this.getClass().getSimpleName());

					enGuestHealthBinahEnt = (GuestHealthBinahDetail) EncryptedDecryptedObjectUtil
							.getEncryptedObject(guestHealthBinahEnt, secretKey, secretIv, isEncryptDecryptDatabaseData);

					enGuestHealthBinahEnt.setUserEmail(user.get().getUserEmail());
					enGuestHealthBinahEnt.setGuest(guestEnt);

					GuestHealthBinahDetail guestHealthBinahDetail = guestHealthBinahDetailRepository
							.save(enGuestHealthBinahEnt);

					if (guestHealthBinahDetail != null) {
						resp.setMsg(addGuestSuccess);
						resp.setSuccess(Boolean.toString(true));
						return resp;
					} else {

						resp.setMsg(addGuestFail);
						resp.setSuccess(Boolean.toString(false));
						return resp;
					}
					
				}
			} else {
				resp.setMsg(addGuestFail);
				resp.setSuccess(Boolean.toString(false));
				return resp;
			}

		} catch (Exception e) {
			resp.setMsg(someThingWentWrong + e);
			resp.setSuccess(Boolean.toString(false));
			System.out.println(e);
			return resp;
		}
		return resp;

	}

	@Override
	public Response removeGuest(String guestId, String userId, String dateTimeFormat) {
		Response resp = new Response();

		Optional<List<Guest>> guestEntityList = guestUserSearchRepository.findGuestByGuestIdAndUserId(guestId, userId,
				Boolean.toString(false));

		if (guestEntityList.get().isEmpty()) {
			resp.setSuccess(Boolean.toString(false));
			resp.setMsg(guestNotExists);
			return resp;
		} else {

			SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
			String currentDate = sdf.format(new Date());

			try {

				String enIsDeleteFlag = (String) EncryptedDecryptedObjectUtil.getEncryptedString(Boolean.toString(true),
						secretKey, secretIv, isEncryptDecryptDatabaseData);
				String enCurrentDate = (String) EncryptedDecryptedObjectUtil.getEncryptedString(currentDate, secretKey,
						secretIv, isEncryptDecryptDatabaseData);
				String enUpdatePName = (String) EncryptedDecryptedObjectUtil.getEncryptedString(
						this.getClass().getSimpleName(), secretKey, secretIv, isEncryptDecryptDatabaseData);

				guestEntityList.get().get(0).setIsdelete(enIsDeleteFlag);
				guestEntityList.get().get(0).setLastUpdateDate(enCurrentDate);
				guestEntityList.get().get(0).setLastUpdatePName(enUpdatePName);

			} catch (Exception e) {
				resp.setSuccess(Boolean.toString(false));
				resp.setMsg(someThingWentWrong + e);
				return resp;
			}

			Guest guest = guestRepository.save(guestEntityList.get().get(0));

			if (guest == null) {
				resp.setSuccess(Boolean.toString(false));
				resp.setMsg(unableToRemoveGuest);
				return resp;
			} else {
				resp.setSuccess(Boolean.toString(true));
				resp.setMsg(removeGuestSuccess);
				return resp;
			}
		}
	}

}
