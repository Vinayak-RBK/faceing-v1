package com.iss.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iss.dao.UserHealthDetailsDao;
import com.iss.dao.UserHealthDetailsResponseDao;
import com.iss.entity.EndUser;
import com.iss.entity.UserHealthDetail;
import com.iss.repository.LoginUserRespository;
import com.iss.repository.UserHealthDetailsRepo;

@Service
public class UserFaceScanHistoryServiceImpl implements UserFaceScanHistoryService{
	
	@Autowired
	private UserHealthDetailsRepo useDetailsRepo;
	
	@Autowired
	private LoginUserRespository loginUserRespository;

	@Override
	public UserHealthDetailsResponseDao getUserAllFaceScanHistoryService(String userId) {
		UserHealthDetailsResponseDao resDao=new UserHealthDetailsResponseDao();
		UserHealthDetailsDao uHealthDao=new UserHealthDetailsDao();
		List<UserHealthDetail> userHealthDetailsList=new ArrayList<UserHealthDetail>();
		List<UserHealthDetailsDao> userHealthDetailsDaosList=new ArrayList<UserHealthDetailsDao>();
		
		userHealthDetailsList=useDetailsRepo.findAllFaceSacnHistoryByUserId(userId);
		
		if(userHealthDetailsList.isEmpty())
		{
			resDao.setMsg("No User Health History found");
			resDao.setSuccess(false);
			return resDao;
		}
		else
		{
			for(UserHealthDetail ent: userHealthDetailsList)
			{
				uHealthDao=new UserHealthDetailsDao();
				
				uHealthDao.setAge(ent.getAge());
				uHealthDao.setGender(ent.getGender());
				uHealthDao.setHeight(ent.getHeight());
				uHealthDao.setWaistCircum(ent.getWaistCircum());
				uHealthDao.setbMICalc(ent.getbMICalc());
				uHealthDao.setaBSI(ent.getaBSI());
				uHealthDao.sethRBPM(ent.gethRBPM());
				uHealthDao.setbPSystolic(ent.getbPSystolic());
				uHealthDao.sethRVSDNN(ent.gethRVSDNN());
				uHealthDao.setbPRPP(ent.getbPRPP());
				uHealthDao.setbPTau(ent.getbPTau());
				uHealthDao.setbPBPM(ent.getbPBPM());
				uHealthDao.settHBCount(ent.gettHBCount());
				uHealthDao.setHealthScore(ent.getHealthScore());
				uHealthDao.setMentalScore(ent.getMentalScore());
				uHealthDao.setVitalScore(ent.getVitalScore());
				uHealthDao.setPhysicalScore(ent.getPhysicalScore());
				uHealthDao.setmSI(ent.getmSI());
				uHealthDao.setBpHeartAttack(ent.getBpHeartAttack());
				uHealthDao.setbPStroke(ent.getbPStroke());;
				uHealthDao.setbPCVD(ent.getbPCVD());
				uHealthDao.setRisksScore(ent.getRisksScore());
				uHealthDao.setsNR(ent.getsNR());
				
				userHealthDetailsDaosList.add(uHealthDao);
				
			}
			
			resDao.setMsg("Successfully sent Data");
			resDao.setSuccess(true);
			resDao.setUseHealthList(userHealthDetailsDaosList);
		}
		
		return resDao;
	}

	@Override
	public UserHealthDetailsResponseDao getUserFaceScanHistoryByScanId(String userId, String healthScanId) {
		UserHealthDetailsResponseDao resDao=new UserHealthDetailsResponseDao();
		UserHealthDetailsDao uHealthDao=new UserHealthDetailsDao();
		List<UserHealthDetail> userHealthDetailsList=new ArrayList<UserHealthDetail>();
		List<UserHealthDetailsDao> userHealthDetailsDaosList=new ArrayList<UserHealthDetailsDao>();
		Optional<EndUser> user=loginUserRespository.findById(userId);
		
		if(user.isEmpty())
		{
			resDao.setMsg("User Doesnot Exists");
			resDao.setSuccess(false);
			return resDao;
		}
		else
		{
			if(healthScanId.isEmpty() || healthScanId==null)
			{
				resDao.setMsg("Invalid User Health Id is received");
				resDao.setSuccess(false);
				return resDao;
			}
		}
		
		userHealthDetailsList=useDetailsRepo.findUserFaceScanHistoryByScanId(userId, healthScanId);
		
		if(userHealthDetailsList.isEmpty())
		{
			resDao.setMsg("No User Health History found");
			resDao.setSuccess(false);
			return resDao;
		}
		else
		{
			for(UserHealthDetail ent: userHealthDetailsList)
			{
				uHealthDao=new UserHealthDetailsDao();
				
				uHealthDao.setAge(ent.getAge());
				uHealthDao.setGender(ent.getGender());
				uHealthDao.setHeight(ent.getHeight());
				uHealthDao.setWaistCircum(ent.getWaistCircum());
				uHealthDao.setbMICalc(ent.getbMICalc());
				uHealthDao.setaBSI(ent.getaBSI());
				uHealthDao.sethRBPM(ent.gethRBPM());
				uHealthDao.setbPSystolic(ent.getbPSystolic());
				uHealthDao.sethRVSDNN(ent.gethRVSDNN());
				uHealthDao.setbPRPP(ent.getbPRPP());
				uHealthDao.setbPTau(ent.getbPTau());
				uHealthDao.setbPBPM(ent.getbPBPM());
				uHealthDao.settHBCount(ent.gettHBCount());
				uHealthDao.setHealthScore(ent.getHealthScore());
				uHealthDao.setMentalScore(ent.getMentalScore());
				uHealthDao.setVitalScore(ent.getVitalScore());
				uHealthDao.setPhysicalScore(ent.getPhysicalScore());
				uHealthDao.setmSI(ent.getmSI());
				uHealthDao.setBpHeartAttack(ent.getBpHeartAttack());
				uHealthDao.setbPStroke(ent.getbPStroke());;
				uHealthDao.setbPCVD(ent.getbPCVD());
				uHealthDao.setRisksScore(ent.getRisksScore());
				uHealthDao.setsNR(ent.getsNR());
				
			}
			
			resDao.setMsg("Successfully sent Data");
			resDao.setSuccess(true);
			resDao.setUseHealthList(userHealthDetailsDaosList);
		}
		
		return resDao;
	}

}
