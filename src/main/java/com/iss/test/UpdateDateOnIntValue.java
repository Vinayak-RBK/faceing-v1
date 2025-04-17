package com.iss.test;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class UpdateDateOnIntValue {
	public static void main(String[] args) {
		int age=10;
		
		LocalDate currentDate = LocalDate.now();
		
		Date date = new Date("1995/01/17");
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int month = localDate.getMonthValue();
		int dat = localDate.getDayOfMonth();
		int year = localDate.getYear();
		
		LocalDate inputDate = LocalDate.of(year-age, month, dat);
		System.out.println(inputDate.toString());
		
	}

}
