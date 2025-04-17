//package com.iss.util;
//
//import java.security.SecureRandom;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//public class OtpUtil {
//
//	@Autowired
//	private JavaMailSender javaMailSender;
//
//	private static final SecureRandom random = new SecureRandom();
//
//	public static String generateOTP() {
//		int otp = 1000 + random.nextInt(9000);
//		return String.valueOf(otp);
//	}
//
//	public void sendOtpToEmail(String emailId, String otp) {
//		try {
//			MimeMessage message = javaMailSender.createMimeMessage();
//			MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//			helper.setTo(emailId);
//			helper.setSubject("Your OTP Code");
//			helper.setText("Dear User,\n\nYour OTP for verification is: " + otp
//					+ "\n\nThis OTP is valid for 10 minutes.\n\nBest Regards,\nYour Application Team");
//
//			javaMailSender.send(message);
//			System.out.println("OTP Sent Successfully to " + emailId);
//		} catch (MessagingException e) {
//			throw new RuntimeException("Error while sending OTP email: " + e.getMessage());
//		}
//		return;
//	}
//}

package com.iss.util;

import java.security.SecureRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class OtpUtil {

    private static final Logger logger = LoggerFactory.getLogger(OtpUtil.class);
    private static final SecureRandom random = new SecureRandom();

    private final JavaMailSender javaMailSender;

    public OtpUtil(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public static String generateOTP() {
        int otp = 1000 + random.nextInt(9000); // Generates a 4-digit OTP
        return String.valueOf(otp);
    }

    public void sendOtpToEmail(String emailId, String otp) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(emailId);
            helper.setSubject("Your OTP Code");
            helper.setText("Dear User,\n\nYour OTP for verification is: " + otp
                    + "\n\nThis OTP is valid for 10 minutes.\n\nBest Regards,\nYour Application Team");

            javaMailSender.send(message);
            logger.info("OTP Sent Successfully to {}", emailId);
        } catch (MessagingException e) {
            logger.error("Error while sending OTP email to {}: {}", emailId, e.getMessage());
            throw new RuntimeException("Error while sending OTP email: " + e.getMessage(), e);
        }
    }
}

