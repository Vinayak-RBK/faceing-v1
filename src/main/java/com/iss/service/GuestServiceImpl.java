package com.iss.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.iss.dao.GuestInfoDao;
import com.iss.dao.Response;
import com.iss.entity.EndUser;
import com.iss.entity.Guest;
import com.iss.entity.GuestHealthDetail;
import com.iss.repository.GuestHealthDetailRepository;
import com.iss.repository.GuestRepository;
import com.iss.repository.LoginUserRespository;

import jakarta.transaction.Transactional;

@Service
public class GuestServiceImpl implements GuestService {

	@Autowired
	private GuestRepository guestRepository;

	@Autowired
	private GuestHealthDetailRepository guestHealthDetailRepository;
	@Autowired
	private LoginUserRespository loginUserRespository;

	@Transactional
	@Override
	public Response addGuest(GuestInfoDao guestInfoDao, String dateTimeFormat) {
		Guest guest = new Guest();
		Response resp = new Response();
		GuestHealthDetail guestHealthEnt = new GuestHealthDetail();
		try {
			Optional<EndUser> user = loginUserRespository.findById(guestInfoDao.getGuestDao().getUserId().toString());

			if (user.isEmpty()) {
				resp.setMsg("Wrong User ID credential is received");
				resp.setSuccess(false);
				return resp;
			}

			SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
			String currentDate = sdf.format(new Date());

			guest.setGuestDOB(guestInfoDao.getGuestDao().getDob());
			guest.setGuestGender(guestInfoDao.getGuestDao().getGender());
			guest.setGuestHeight(guestInfoDao.getGuestDao().getHeight());
			guest.setGuestWeight(guestInfoDao.getGuestDao().getWeight());
			guest.setGuestName(guestInfoDao.getGuestDao().getName());
			guest.setGuestImage(guestInfoDao.getGuestDao().getImage());
			guest.setIsdelete(false);
			guest.setUser(user.get());
			guest.setRegistDate(currentDate);
			guest.setRegistPName(this.getClass().getSimpleName());
			guest.setLastUpdateDate(currentDate);
			guest.setLastUpdatePName(this.getClass().getSimpleName());

			System.out.println("This is addGuest method");

			Guest guestEnt = guestRepository.save(guest);

			if (guestEnt != null) {

				guestHealthEnt.setGuest(guestEnt);
				guestHealthEnt.setGuestAge(guestInfoDao.getGuestHealthDao().getGuestAge());
				guestHealthEnt.setGuestBloodPressure(guestInfoDao.getGuestHealthDao().getGuestBloodPressure());
				guestHealthEnt.setGuestBodyMassIndex(guestInfoDao.getGuestHealthDao().getGuestBodyMassIndex());
				guestHealthEnt.setGuestBodyShapeIndex(guestInfoDao.getGuestHealthDao().getGuestBodyShapeIndex());
				guestHealthEnt.setGuestEnergyLevel(guestInfoDao.getGuestHealthDao().getGuestEnergyLevel());
				guestHealthEnt.setGuestFitnessLevel(guestInfoDao.getGuestHealthDao().getGuestFitnessLevel());
				guestHealthEnt.setGuestHeartRate(guestInfoDao.getGuestHealthDao().getGuestHeartRate());
				guestHealthEnt.setGuestHemoglobinLevel(guestInfoDao.getGuestHealthDao().getGuestHemoglobinLevel());
				guestHealthEnt.setGuestHRVData(guestInfoDao.getGuestHealthDao().getGuestHRVData());
				guestHealthEnt.setGuestOxygenSaturation(guestInfoDao.getGuestHealthDao().getGuestOxygenSaturation());
				guestHealthEnt.setGuestRelaxationLevel(guestInfoDao.getGuestHealthDao().getGuestRelaxationLevel());
				guestHealthEnt.setGuestRespirationRate(guestInfoDao.getGuestHealthDao().getGuestRespirationRate());
				guestHealthEnt.setGuestStressLevel(guestInfoDao.getGuestHealthDao().getGuestStressLevel());

				GuestHealthDetail guestHealthDetail = guestHealthDetailRepository.save(guestHealthEnt);

				if (guestHealthDetail != null) {
					resp.setMsg("Successfully created Guest");
					resp.setSuccess(true);
					return resp;
				} else {

					resp.setMsg("Unable to add Guest");
					resp.setSuccess(false);
					return resp;
				}
			} else {
				resp.setMsg("Unable to add Guest");
				resp.setSuccess(false);
				return resp;
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return resp;

	}

}
