package com.iss.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.iss.dao.GuestDao;
import com.iss.dao.GuestInfoListDao;
import com.iss.entity.Guest;
import com.iss.repository.GuestUserSearchRepository;

@Service
public class GuestUserSearchServiceImpl implements GuestUserSearchService {

	@Autowired
	private GuestUserSearchRepository guestUserSearchRepository;

	@Override
	public GuestInfoListDao findAllGuest(String userId) {
		GuestInfoListDao guestListRespDao = new GuestInfoListDao();
		Optional<List<Guest>> guestEntityList = guestUserSearchRepository.findByUserId(userId, false);
		List<GuestDao> guestList = new ArrayList<>();

		if (!guestEntityList.isEmpty()) {
			for (Guest ent : guestEntityList.get()) {

				GuestDao dto = new GuestDao();
				dto.setName(ent.getGuestName());
				dto.setGender(ent.getGuestGender());
				dto.setHeight(ent.getGuestHeight());
				dto.setWeight(ent.getGuestWeight());
				dto.setDob(ent.getGuestDOB().toString());
				dto.setImage(ent.getGuestImage());
				dto.setUserId(ent.getEndUser().getUserId());
				dto.setGuestId(ent.getGuestId().toString());

				guestList.add(dto);
			}
			guestListRespDao.setMsg("Guest Records found");
			guestListRespDao.setSuccess(true);
			return guestListRespDao;

		} else {
			guestListRespDao.setMsg("Guest Records not found");
			guestListRespDao.setSuccess(false);
			return guestListRespDao;
		}

	}

//	@Override
//	public List<GuestUserSearchDao> findAllGuest(GuestDao guestDao) {
//		List<CommonTable> guestList=guestUserSearchRepository.findByGuestName(guestDao.getGuestName(),guestDao.getUserEmail());
//		
//		int count=1;
//		if(!guestList.isEmpty())
//		{
//			System.out.println("List is : ");
////			System.out.println(guestList);
//			for(CommonTable common:guestList)
//			{
//				count++;
//				System.out.println(common.getGuestId());
//				System.out.println(common.getUserEmail());
//				System.out.println(common.getGuestName());
//				System.out.println(common.getGuestGender());
//				System.out.println(common.getGuestDOB());
//				System.out.println(common.getGuestWeight());
//				System.out.println(common.getGuestHeight());
//				System.out.println(common.getGuestImage());
//				System.out.println(common.getGuestCurrentHealthCond());
//				System.out.println(common.getGuestCurrentMedication());
//				System.out.println(common.getGuestDrinkAlcohol());
//				System.out.println(common.getUserId());
//				System.out.println(common.getRegistDate());
//				System.out.println(common.getRegistPName());
//				System.out.println(common.getLastUpdateDate());
//				System.out.println(common.getLastUpdatePName());
//				System.out.println(common.getGuestRespirationRate());
//				System.out.println(common.getGuestBloodPressure());
//				System.out.println(common.getGuestHeartRate());
//				System.out.println(common.getGuestOxygenSaturation());
//				System.out.println(common.getGuestHRVData());
//				System.out.println(common.getGuestStressLevel());
//				System.out.println(common.getGuestRelaxationLevel());
//				System.out.println(common.getGuestEnergyLevel());
//				System.out.println(common.getGuestBodyShapeIndex());
//				System.out.println(common.getGuestBodyMassIndex());
//				System.out.println(common.getGuestAge());
//				System.out.println(common.getGuestHemoglobinLevel());
//				System.out.println(common.getGuestFitnessLevel());
////				guestUserSearchDaos.add(modelMapper.map(guest, GuestUserSearchDao.class));
////				System.out.println("Guest is : "+guest);
////				System.out.println("Guest health details is : "+guest.getGuestHealthDetail());
//			}
//		}
//		System.out.println("Count is : "+count);
//		
//		return guestUserSearchDaos;
//	}

}
