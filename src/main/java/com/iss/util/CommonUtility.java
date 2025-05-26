package com.iss.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.Hashtable;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.iss.dao.EmployeeDao;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class CommonUtility {

	private static final Logger logger = LoggerFactory.getLogger(CommonUtility.class);
	private static final SecureRandom random = new SecureRandom();

	private final JavaMailSender javaMailSender;

	public CommonUtility(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public static String generateOTP() {
		logger.info("Generating new OTP");
		int otp = 1000 + random.nextInt(9000); // Generates a 4-digit OTP
		logger.info("Generated OTP is - {}", otp);
		return String.valueOf(otp);
	}

	public boolean sendOtpToEmail(String emailId, String otp) {
		logger.info("Sending otp to Email Started...");
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setTo(emailId);
			helper.setSubject("Your OTP Code");
			helper.setText("Dear User,\n\nYour OTP for verification is: " + otp
					+ "\n\nThis OTP is valid for 10 minutes.\n\nBest Regards,\nYour Application Team", true);

			javaMailSender.send(message);
			logger.info("OTP {} Sent Successfully to {}", otp, emailId);
			return true;
		} catch (MessagingException e) {
			logger.error("Error while sending OTP email to {}: {}", emailId, e.getMessage());
//            throw new RuntimeException("Error while sending OTP email: " + e.getMessage(), e);
			return false;
		}
	}

	public boolean sendOnboardingSuccessToEmail(String emailId) {
		logger.info("Sending Onboarding Success to Email Started...");
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			StringBuilder sb = new StringBuilder();

			helper.setTo(emailId);
			helper.setSubject("Onboarding Success Mail");

			sb.append("<html><body><div>");
			sb.append("<h3>Welcome " + emailId + "</h3>");
			sb.append("<h4>Your Onboarding is completed Successfully...</h4>");
			sb.append("</div></html></body>");

			helper.setText(sb.toString(), true);

			javaMailSender.send(message);
			logger.info("Onboarding is Successful for the Email - {}", emailId);
			return true;
		} catch (MessagingException e) {
			logger.error("Error while sending OTP email to {}: {}", emailId, e.getMessage());
//            throw new RuntimeException("Error while sending OTP email: " + e.getMessage(), e);
			return false;
		}
	}

	public boolean sendEmployeeDetailsToEmail(String emailId, EmployeeDao empDao) {
		logger.info("Sending Employee Details to Email Started...");
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			StringBuilder sb = new StringBuilder();
			helper.setTo(emailId);
			helper.setSubject("Your Created Employee Details By Admin");

			sb.append("<html><body><div>");
			sb.append("<h3>Employee Details : </h3>");
			sb.append("<br><b>Name : </b>" + empDao.getName());
			sb.append("<br><b>jobRole : </b>" + empDao.getJobRole());
			sb.append("<br><b>Email Id : </b>" + empDao.getEmail());
			sb.append("<br><b>Password : </b>" + empDao.getPassword());
			sb.append("<br><b>Mobile Number : </b>" + empDao.getMobileNumber());
			sb.append("</div></html></body>");
			helper.setText(sb.toString(), true);

			javaMailSender.send(message);
			logger.info("Employee details is sent to Email ID - {}", emailId);
			return true;
		} catch (MessagingException e) {
			logger.error("Error while sending OTP email to {}: {}", emailId, e.getMessage());
//            throw new RuntimeException("Error while sending OTP email: " + e.getMessage(), e);
			return false;
		}
	}

	public static boolean checkExistanceOfMail(String emailId) {
		boolean isEmailExits = isEmailValid(emailId);
		return isEmailExits;
	}

	public static boolean isEmailValid(String email) {
		try {
			// Extract domain from email
			String domain = email.substring(email.indexOf("@") + 1);

			// Get MX records
			String mxRecord = getMXRecord(domain);
			if (mxRecord == null) {
				System.out.println("No MX records found for domain: " + domain);
				return false;
			}

			// Connect to the SMTP server
			return verifyEmailWithSMTP(mxRecord, email);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private static String getMXRecord(String domain) throws NamingException {
		Hashtable<String, String> env = new Hashtable<>();
		env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
		DirContext ctx = new InitialDirContext(env);
		Attributes attributes = ctx.getAttributes(domain, new String[] { "MX" });

		Attribute attr = attributes.get("MX");
		if (attr == null || attr.size() == 0) {
			return null;
		}

		// Extract MX record (first one)
		String mxRecord = (String) attr.get(0);
		return mxRecord.split(" ")[1]; // Extract mail server address
	}

	private static boolean verifyEmailWithSMTP(String mailServer, String email) {
		try (Socket socket = new Socket(mailServer, 25);
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

			// Read server response
			if (!reader.readLine().startsWith("220")) {
				return false;
			}

			// Send HELO command
			socket.getOutputStream().write("HELO example.com\r\n".getBytes());
			if (!reader.readLine().startsWith("250")) {
				return false;
			}

			// Send MAIL FROM
			socket.getOutputStream().write("MAIL FROM:<test@example.com>\r\n".getBytes());
			if (!reader.readLine().startsWith("250")) {
				return false;
			}

			// Send RCPT TO (Check if recipient exists)
			socket.getOutputStream().write(("RCPT TO:<" + email + ">\r\n").getBytes());
			String response = reader.readLine();

			// Close the connection
			socket.getOutputStream().write("QUIT\r\n".getBytes());

			// Response 250 means email exists
			return response.startsWith("250");

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static float getAgeDifferencesByDates(String dob) {
		@SuppressWarnings("deprecation")
		Date date = new Date(dob);
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		int month = localDate.getMonthValue();
		int dat = localDate.getDayOfMonth();
		int year = localDate.getYear();

		LocalDate inputDate = LocalDate.of(year, month, dat);
		LocalDate currentDate = LocalDate.now();
		float dobYears = Period.between(inputDate, currentDate).getYears();
		float dobMonths = Period.between(inputDate, currentDate).getMonths();

		float diffAge = (dobYears + (dobMonths / 12));
		return Math.round(diffAge * 100.0) / 100.0f;
	}

//	public static String getFormatedDateToString(String inputDate) {
//		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
////        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyyMMddhhmmss");
////		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("hh:mm");
//		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
//		// Parse and format the date
//		LocalDateTime dateTime = LocalDateTime.parse(inputDate, inputFormatter);
//		String outputDate = dateTime.format(outputFormatter);
//		return outputDate;
//	}

}
