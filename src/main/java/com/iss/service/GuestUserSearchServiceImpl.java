package com.iss.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.iss.dao.GuestDao;
import com.iss.dao.GuestHealthAnuraDetailsDao;
import com.iss.dao.GuestHealthBinahDetailsDao;
import com.iss.dao.GuestHealthInfoDao;
import com.iss.dao.GuestInfoListDao;
import com.iss.dao.PagerRequestDao;
import com.iss.dao.PagerResponseDao;
import com.iss.entity.EndUser;
import com.iss.entity.Guest;
import com.iss.entity.GuestHealthAnuraDetail;
import com.iss.entity.GuestHealthBinahDetail;
import com.iss.properties.FieldNames;
import com.iss.repository.GuestHealthAnuraDetailRepository;
import com.iss.repository.GuestHealthBinahDetailRepository;
import com.iss.repository.GuestUserSearchRepository;
import com.iss.repository.LoginUserRepositoryImp;
import com.iss.repository.LoginUserRespository;
import com.iss.util.CommonUtility;
import com.iss.util.DateFormatUtil;
import com.iss.util.EncryptedDecryptedObjectUtil;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class GuestUserSearchServiceImpl implements GuestUserSearchService {

	@Value("${GUEST_RECORDS_FOUND}")
	private String guestRecordsFound;

	@Value("${GUEST_RECORDS_NOT_FOUND}")
	private String guestRecordsNotFound;

	@Value("${GUEST_HEALTH_RECORDS_NOT_FOUND}")
	private String guestHealthRecordsNotFound;

	@Value("${SOMETHING_WENT_WRONG}")
	private String someThingWentWrong;

	@Value("${WRONG_USERID_RECEIVED}")
	private String wrongUserId;

	@Value("${SECRET_KEY}")
	private String secretKey;

	@Value("${SECRET_IV}")
	private String secretIv;

	@Value("${IS_ENCRYPT_DECRYPT_ENABLE_DATABASE_DATA}")
	private boolean isEncryptDecryptDatabaseData;

	@Value("${DATE_TIME_FORMAT_EXPORTED_FILES}")
	private String dateFormatExportedFiles;

	@Autowired
	private GuestUserSearchRepository guestUserSearchRepository;

	@Autowired
	private GuestHealthAnuraDetailRepository guestHealthAnuraDetailRepository;

	@Autowired
	private GuestHealthBinahDetailRepository guestHealthBinahDetailRepository;

	@Autowired
	private LoginUserRespository loginUserRespository;

	@Autowired
	private LoginUserRepositoryImp loginUserRepositoryImp;

	@Override
	public GuestInfoListDao findAllGuest(String userId) {

		GuestInfoListDao guestListRespDao = new GuestInfoListDao();
		GuestDao dto = new GuestDao();
		GuestDao enDto = new GuestDao();
		Optional<List<Guest>> guestEntityList = guestUserSearchRepository.findByUserId(userId, Boolean.toString(false));
		List<GuestDao> guestList = new ArrayList<>();

		if (!guestEntityList.get().isEmpty()) {
			for (Guest ent : guestEntityList.get()) {

				dto = new GuestDao();
				dto.setName(ent.getGuestName());
				dto.setGender(ent.getGuestGender());
				dto.setHeight(ent.getGuestHeight());
				dto.setWeight(ent.getGuestWeight());
				dto.setDob(ent.getGuestDOB().toString());
				dto.setUserId(ent.getEndUser().getUserId().toString());
				dto.setScannedDate(DateFormatUtil.getFormatedDateToString(ent.getRegistDate()));
				dto.setAge(Float.toString(CommonUtility.getAgeDifferencesByDates(ent.getGuestDOB())));

				try {
					enDto = (GuestDao) EncryptedDecryptedObjectUtil.getDecryptedObject(dto, secretKey, secretIv,
							isEncryptDecryptDatabaseData);
					dto.setGuestId(ent.getGuestId().toString());
				} catch (Exception e) {
					guestListRespDao.setMsg(someThingWentWrong + e);
					guestListRespDao.setSuccess(Boolean.toString(false));
					return guestListRespDao;
				}

				guestList.add(enDto);
			}
			guestListRespDao.setMsg(guestRecordsFound);
			guestListRespDao.setGuestListDao(guestList);
			guestListRespDao.setSuccess(Boolean.toString(true));
			return guestListRespDao;

		} else {
			guestListRespDao.setMsg(guestRecordsNotFound);
			guestListRespDao.setSuccess(Boolean.toString(false));
			return guestListRespDao;
		}

	}

	@Override
	public GuestInfoListDao findGuestByGuestIdAndUserId(String guestId, String userId) {

		GuestInfoListDao guestListRespDao = new GuestInfoListDao();
		String enSdkType = "";
		GuestDao dto = new GuestDao();
		GuestDao decDto = new GuestDao();

		Optional<EndUser> user = loginUserRespository.findById(userId);

		if (user.isEmpty()) {
			guestListRespDao.setMsg(wrongUserId);
			guestListRespDao.setSuccess(Boolean.toString(false));
			return guestListRespDao;
		}

		Optional<List<Guest>> guestEntityList = guestUserSearchRepository.findGuestByGuestIdAndUserId(guestId,
				user.get().getUserId().toString(), Boolean.toString(false));
		List<GuestDao> guestList = new ArrayList<>();
		ModelMapper mapper = new ModelMapper();

		if (!guestEntityList.get().isEmpty()) {

			try {
				enSdkType = (String) EncryptedDecryptedObjectUtil.getDecryptedObject(user.get().getsDKType(), secretKey,
						secretIv, isEncryptDecryptDatabaseData);
			} catch (Exception e1) {
				guestListRespDao.setMsg("Error occured during encryption " + e1);
				guestListRespDao.setSuccess(Boolean.toString(false));
				return guestListRespDao;
			}

			for (Guest ent : guestEntityList.get()) {
				if (FieldNames.SDK_ANURA.equals(enSdkType)) {
					dto = new GuestDao();
					decDto = new GuestDao();
					GuestHealthAnuraDetail guestHealthAnuraDetail = guestHealthAnuraDetailRepository
							.findByGuestId(guestId);

					if (guestHealthAnuraDetail == null) {
						guestListRespDao.setMsg(guestHealthRecordsNotFound);
						guestListRespDao.setSuccess(Boolean.toString(false));
						return guestListRespDao;
					} else {

						dto.setName(ent.getGuestName());
						dto.setGender(ent.getGuestGender());
						dto.setHeight(ent.getGuestHeight());
						dto.setWeight(ent.getGuestWeight());
						dto.setDob(ent.getGuestDOB().toString());
						dto.setUserId(ent.getEndUser().getUserId().toString());

						try {
							decDto = (GuestDao) EncryptedDecryptedObjectUtil.getDecryptedObject(dto, secretKey,
									secretIv, isEncryptDecryptDatabaseData);
							dto.setGuestId(ent.getGuestId().toString());
						} catch (Exception e) {
							guestListRespDao.setMsg(someThingWentWrong + e);
							guestListRespDao.setSuccess(Boolean.toString(false));
							return guestListRespDao;
						}

						guestList.add(decDto);

						GuestHealthAnuraDetailsDao guestHealthAnuraDao = new GuestHealthAnuraDetailsDao();
						GuestHealthAnuraDetailsDao decGuestHealthAnuraDao = new GuestHealthAnuraDetailsDao();

						guestHealthAnuraDao = mapper.map(guestHealthAnuraDetail, GuestHealthAnuraDetailsDao.class);
						guestHealthAnuraDao.setUserEmail(ent.getUserEmail());
						try {
							decGuestHealthAnuraDao = (GuestHealthAnuraDetailsDao) EncryptedDecryptedObjectUtil
									.getDecryptedObject(guestHealthAnuraDao, secretKey, secretIv,
											isEncryptDecryptDatabaseData);
						} catch (Exception e) {
							guestListRespDao.setMsg(someThingWentWrong + e);
							guestListRespDao.setSuccess(Boolean.toString(false));
							return guestListRespDao;
						}

						guestListRespDao.setGuestListDao(guestList);
						guestListRespDao.setGuestHealthAnuraDetail(decGuestHealthAnuraDao);

					}

				} else if (FieldNames.SDK_BINAH.equals(enSdkType)) {
					dto = new GuestDao();
					decDto = new GuestDao();
					GuestHealthBinahDetail guestHealthBinahDetail = guestHealthBinahDetailRepository
							.findByGuestId(guestId);

					if (guestHealthBinahDetail == null) {
						guestListRespDao.setMsg(guestHealthRecordsNotFound);
						guestListRespDao.setSuccess(Boolean.toString(false));
						return guestListRespDao;
					} else {

						dto.setName(ent.getGuestName());
						dto.setGender(ent.getGuestGender());
						dto.setHeight(ent.getGuestHeight());
						dto.setWeight(ent.getGuestWeight());
						dto.setDob(ent.getGuestDOB().toString());
						dto.setUserId(ent.getEndUser().getUserId().toString());

						try {
							decDto = (GuestDao) EncryptedDecryptedObjectUtil.getDecryptedObject(dto, secretKey,
									secretIv, isEncryptDecryptDatabaseData);
							dto.setGuestId(ent.getGuestId().toString());
						} catch (Exception e) {
							guestListRespDao.setMsg(someThingWentWrong + e);
							guestListRespDao.setSuccess(Boolean.toString(false));
							return guestListRespDao;
						}

						guestList.add(decDto);

						GuestHealthBinahDetailsDao guestHealthAnuraDao = new GuestHealthBinahDetailsDao();
						GuestHealthBinahDetailsDao decGuestHealthAnuraDao = new GuestHealthBinahDetailsDao();

						guestHealthAnuraDao = mapper.map(guestHealthBinahDetail, GuestHealthBinahDetailsDao.class);
						guestHealthAnuraDao.setUserEmail(ent.getUserEmail());
						try {
							decGuestHealthAnuraDao = (GuestHealthBinahDetailsDao) EncryptedDecryptedObjectUtil
									.getDecryptedObject(guestHealthAnuraDao, secretKey, secretIv,
											isEncryptDecryptDatabaseData);
						} catch (Exception e) {
							guestListRespDao.setMsg(someThingWentWrong + e);
							guestListRespDao.setSuccess(Boolean.toString(false));
							return guestListRespDao;
						}

						guestListRespDao.setGuestListDao(guestList);
						guestListRespDao.setGuestHealthBinahDetail(decGuestHealthAnuraDao);

					}
				}

				guestListRespDao.setMsg(guestRecordsFound);
				guestListRespDao.setSuccess(Boolean.toString(true));
				return guestListRespDao;

			}

		} else {
			guestListRespDao.setMsg(guestRecordsNotFound);
			guestListRespDao.setSuccess(Boolean.toString(false));
			return guestListRespDao;
		}
		return guestListRespDao;

	}

	@Override
	public GuestInfoListDao getGuestDetailsByExcel(String userId, HttpServletResponse response) {
		List<GuestHealthInfoDao> healthInfoDaoList = new ArrayList<GuestHealthInfoDao>();
		GuestHealthInfoDao guestHealthInfoDao = new GuestHealthInfoDao();

		GuestInfoListDao infoListDao = new GuestInfoListDao();
		GuestHealthAnuraDetail guestHealthAnuraDetail = new GuestHealthAnuraDetail();
		GuestHealthBinahDetail guestHealthBinahDetail = new GuestHealthBinahDetail();

		GuestHealthAnuraDetailsDao guestHealthAnuraDetailDao = new GuestHealthAnuraDetailsDao();
		GuestHealthBinahDetailsDao guestHealthBinahDetailDao = new GuestHealthBinahDetailsDao();

		GuestHealthAnuraDetailsDao decGuestHealthAnuraDetailDao = new GuestHealthAnuraDetailsDao();
		GuestHealthBinahDetailsDao decGuestHealthBinahDetailDao = new GuestHealthBinahDetailsDao();

		GuestDao guestDao = new GuestDao();
		GuestDao decGuestDao = new GuestDao();

		ModelMapper mapper = new ModelMapper();

		Optional<EndUser> user = loginUserRespository.findById(userId);

		if (user.isEmpty()) {
			infoListDao.setMsg("No user found.");
			infoListDao.setSuccess(Boolean.toString(false));
			return infoListDao;
		}
		try {
			String enIsDelete = (String) EncryptedDecryptedObjectUtil.getEncryptedString(Boolean.toString(false),
					secretKey, secretIv, isEncryptDecryptDatabaseData);

			Optional<List<Guest>> guestList = guestUserSearchRepository.findByUserId(userId, enIsDelete);

			if (guestList.isEmpty()) {
				infoListDao.setMsg("No Guest data found.");
				infoListDao.setSuccess(Boolean.toString(false));
				return infoListDao;
			}
			String enSDKType = (String) EncryptedDecryptedObjectUtil.getDecryptedString(user.get().getsDKType(),
					secretKey, secretIv, isEncryptDecryptDatabaseData);
			for (Guest guestEnt : guestList.get()) {
				guestDao = new GuestDao();
				guestHealthInfoDao = new GuestHealthInfoDao();

				guestDao.setName(guestEnt.getGuestName());
				guestDao.setGender(guestEnt.getGuestGender());
				guestDao.setHeight(guestEnt.getGuestHeight());
				guestDao.setWeight(guestEnt.getGuestWeight());
				guestDao.setDob(guestEnt.getGuestDOB());
				guestDao.setGuestId(guestEnt.getGuestId().toString());
				guestDao.setScannedDate(guestEnt.getRegistDate());

				if (FieldNames.SDK_ANURA.equals(enSDKType)) {
					guestHealthAnuraDetail = guestHealthAnuraDetailRepository
							.findByGuestId(guestEnt.getGuestId().toString());
					if (guestHealthAnuraDetail != null) {
						guestHealthAnuraDetailDao = mapper.map(guestHealthAnuraDetail,
								GuestHealthAnuraDetailsDao.class);

						decGuestHealthAnuraDetailDao = (GuestHealthAnuraDetailsDao) EncryptedDecryptedObjectUtil
								.getDecryptedObject(guestHealthAnuraDetailDao, secretKey, secretIv,
										isEncryptDecryptDatabaseData);

						guestHealthInfoDao.setAnuraDetails(decGuestHealthAnuraDetailDao);
					}
				} else if (FieldNames.SDK_BINAH.equals(enSDKType)) {
					guestHealthBinahDetail = guestHealthBinahDetailRepository
							.findByGuestId(guestEnt.getGuestId().toString());

					guestHealthBinahDetailDao = mapper.map(guestHealthBinahDetail, GuestHealthBinahDetailsDao.class);

					decGuestHealthBinahDetailDao = (GuestHealthBinahDetailsDao) EncryptedDecryptedObjectUtil
							.getDecryptedObject(guestHealthBinahDetailDao, secretKey, secretIv,
									isEncryptDecryptDatabaseData);

					guestHealthInfoDao.setBinahDetails(decGuestHealthBinahDetailDao);
				}
				decGuestDao = (GuestDao) EncryptedDecryptedObjectUtil.getDecryptedObject(guestDao, secretKey, secretIv,
						isEncryptDecryptDatabaseData);
				guestHealthInfoDao.setGuestDao(decGuestDao);
				healthInfoDaoList.add(guestHealthInfoDao);
			}

			if (healthInfoDaoList.isEmpty()) {
				infoListDao.setMsg("Guests data not found");
				infoListDao.setSuccess(Boolean.toString(false));
			} else {
				infoListDao.setMsg("Guests data found");
				infoListDao.setSuccess(Boolean.toString(true));
				infoListDao.setHealthInfoDaoList(healthInfoDaoList);
			}
		} catch (Exception e) {
			System.out.println(e);
			infoListDao.setMsg("Unbale to export the file.");
			infoListDao.setSuccess(Boolean.toString(false));
		}
		return infoListDao;
	}

	@SuppressWarnings("removal")
	@Override
	public PagerResponseDao findAllGuestByUserId(PagerRequestDao pagerDao) {
		GuestDao dto = new GuestDao();
		GuestDao enDto = new GuestDao();
		PagerResponseDao respDao = new PagerResponseDao();
		List<Guest> entList = new ArrayList<Guest>();
		List<GuestDao> guestList = new ArrayList<GuestDao>();

		String enIsDelete = "";
		String enSearchBy = "";

		int totalUsersCount = 0;

		try {
			enIsDelete = (String) EncryptedDecryptedObjectUtil.getEncryptedString(Boolean.toString(false), secretKey,
					secretIv, isEncryptDecryptDatabaseData);
			if (!pagerDao.getSearchBy().isEmpty()) {
				enSearchBy = (String) EncryptedDecryptedObjectUtil.getEncryptedString(pagerDao.getSearchBy(), secretKey,
						secretIv, isEncryptDecryptDatabaseData);
				pagerDao.setSearchBy(enSearchBy);
			}

		} catch (Exception e1) {
			respDao.setSuccess(Boolean.toString(false));
			respDao.setMsg("Some thing went wrong during encryption " + e1);
			return respDao;
		}

		// offset Value
		int pageNo = new Integer(pagerDao.getPageNo());

		// size
		int size = new Integer(pagerDao.getPageSize());

		totalUsersCount = (int) loginUserRepositoryImp.getCountOfGuests(enIsDelete, pagerDao);

		if (totalUsersCount == 0) {
			respDao.setSuccess(Boolean.toString(false));
			respDao.setMsg("Guest records not found...");
			return respDao;
		}

		entList = loginUserRepositoryImp.getPersonalDetailsOfGuest(enIsDelete, pagerDao, size, (pageNo - 1) * size);

		if (!entList.isEmpty()) {
			for (Guest ent : entList) {

				dto = new GuestDao();
				dto.setName(ent.getGuestName());
				dto.setGender(ent.getGuestGender());
				dto.setHeight(ent.getGuestHeight());
				dto.setWeight(ent.getGuestWeight());
				dto.setDob(ent.getGuestDOB().toString());

				try {
					enDto = (GuestDao) EncryptedDecryptedObjectUtil.getDecryptedObject(dto, secretKey, secretIv,
							isEncryptDecryptDatabaseData);

					String decRegistDate = (String) EncryptedDecryptedObjectUtil.getDecryptedString(ent.getRegistDate(),
							secretKey, secretIv, isEncryptDecryptDatabaseData);

					String decGuestDob = (String) EncryptedDecryptedObjectUtil.getDecryptedString(ent.getGuestDOB(),
							secretKey, secretIv, isEncryptDecryptDatabaseData);

					dto.setScannedDate(DateFormatUtil.getFormatedDateToString(decRegistDate));
					dto.setAge(Float.toString(CommonUtility.getAgeDifferencesByDates(decGuestDob)));
					dto.setGuestId(ent.getGuestId().toString());
				} catch (Exception e) {
					respDao.setMsg(someThingWentWrong + e);
					System.out.println(e);
					respDao.setSuccess(Boolean.toString(false));
					return respDao;
				}

				guestList.add(enDto);
			}
			respDao.setMsg(guestRecordsFound);
			respDao.setTotalCount(Integer.toString(totalUsersCount));
			double b = (double) totalUsersCount / (double) size;
			respDao.setNoOfPages((new Integer((int) Math.ceil(b))).toString());
			respDao.setCurPageNo(pagerDao.getPageNo());
			respDao.setGuestListDao(guestList);
			respDao.setSuccess(Boolean.toString(true));
			return respDao;

		} else {
			respDao.setMsg(guestRecordsNotFound);
			respDao.setSuccess(Boolean.toString(false));
			return respDao;
		}

	}

}
