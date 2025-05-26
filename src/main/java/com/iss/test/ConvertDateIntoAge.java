package com.iss.test;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class ConvertDateIntoAge {

	
	public static void main(String[] args) {
		Date date = new Date("1995/01/17");
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int month = localDate.getMonthValue();
		int dat = localDate.getDayOfMonth();
		int year = localDate.getYear();
		
		LocalDate inputDate = LocalDate.of(year, month, dat);
        LocalDate currentDate = LocalDate.now();
//        
//        // Calculate the period between the dates
        Period period = Period.between(inputDate, currentDate);
//        
//        // Get the difference in years
        int years = period.getYears();
//        
        System.out.println("Age diff is : "+period.getYears());
		
    }
}
