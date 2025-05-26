package com.iss.test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormatConverter {
	public static void main(String[] args) {
        String inputDate = "2025/05/25"; // yyyy/MM/dd

        // Define input and output formatters
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Parse and format
        LocalDate date = LocalDate.parse(inputDate, inputFormatter);
        String formattedDate = date.format(outputFormatter);

        System.out.println("Formatted Date: " + formattedDate);
    }

}
