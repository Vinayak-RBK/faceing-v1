package com.iss.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class DateFormatUtil {
	
	public static String getFormatedDateToString(String inputDate) {
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyyMMddhhmmss");
//		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("hh:mm");
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		// Parse and format the date
		LocalDateTime dateTime = LocalDateTime.parse(inputDate, inputFormatter);
		String outputDate = dateTime.format(outputFormatter);
		return outputDate;
	}
	
	public static String getFormatedDateToStringFormat1(String inputDate) {
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate date = LocalDate.parse(inputDate, inputFormatter);
        String outputDate = date.format(outputFormatter);

		return outputDate;
	}

}
