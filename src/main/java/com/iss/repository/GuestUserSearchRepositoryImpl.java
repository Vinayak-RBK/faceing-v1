package com.iss.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.iss.entity.Guest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Repository
public class GuestUserSearchRepositoryImpl {

	@Autowired
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<Guest> findByGuestName(String name) {
		List<Guest> guestList=new ArrayList<Guest>();

		StringBuilder sb = new StringBuilder();

		sb.append(
				"Select guests.user_id,guests.guest_id,guests.user_email_id,guest_name,guest_gender,guest_dob,guest_weight,guest_height,guest_image,"
				+ "guest_current_health_cond,guest_current_medication,guest_drink_alcohol,guests.last_update_date,guests.last_update_pname,"
				+ "guests.regist_date,guests.regist_pname  "
						+ "from guests inner join users on  guests.user_id=users.user_id");

		Query query = em.createNativeQuery(sb.toString(), Guest.class);

		guestList = (List<Guest>)query.getResultList();
		
		System.out.println("List is : " + guestList.size());
		System.out.println(guestList.get(0).getGuestName());
		
		for(Guest guest:guestList)
		{
			System.out.println(guest.getGuestName()+", "+guest.getGuestWeight());
		}

		
		return new ArrayList<Guest>();
	}

}
