package com.iss.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.iss.properties.DateTimeFormat;

@Component
public class DateFormatUtil {
	
	public static String getFormatedDateToString(String inputDate) {
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(DateTimeFormat.getDateTimeFormat1());
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(DateTimeFormat.getDateTimeFormat1());
		
		// Parse and format the date
		LocalDateTime dateTime = LocalDateTime.parse(inputDate, inputFormatter);
		String outputDate = dateTime.format(outputFormatter);
		return outputDate;
	}
	
	public static String getFormatedDateToStringFormat1(String inputDate) {
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(DateTimeFormat.getDateTimeFormat2());
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(DateTimeFormat.getDateTimeFormat2());

        LocalDate date = LocalDate.parse(inputDate, inputFormatter);
        String outputDate = date.format(outputFormatter);

		return outputDate;
	}

}
