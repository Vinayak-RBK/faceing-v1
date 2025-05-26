package com.iss.util;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncryptDecryptDataUtil {

	private static final Logger logger = LoggerFactory.getLogger(EncryptDecryptDataUtil.class);

	// Encrypt Data
	public static Object encrypt(String value, String secretkey, String secretIv) throws Exception {

		logger.info("Encryption started for the String Value - {}", value);

		try {
			String jsonValue = new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(value);

			IvParameterSpec iv = new IvParameterSpec(secretIv.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(secretkey.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			byte[] encrypted = cipher.doFinal(jsonValue.getBytes("UTF-8"));

			String enbyte = Base64.getEncoder().encodeToString(encrypted);

			logger.info("Encrypted Value of {} is {}", value, enbyte);
			return enbyte;

		} catch (Exception ex) {
			logger.info("Exception occurred while Encrypting the Value {}", value);
		}
		return "";
	}

	// Decrypt Data
	public static Object decrypt(String encryptedValue, String secretkey, String secretIv) throws Exception {

		logger.info("Decryption started for the object - {}", encryptedValue);

		try {
			IvParameterSpec iv = new IvParameterSpec(secretIv.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(secretkey.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

			byte[] decoded = Base64.getDecoder().decode(encryptedValue);
			byte[] original = cipher.doFinal(decoded);

			String jsonValue = new String(original, "UTF-8");
			
			String decValue=new com.fasterxml.jackson.databind.ObjectMapper().readValue(jsonValue, String.class);
			
			logger.info("Decrypted value of String {} is {}",encryptedValue,decValue);
			
			return decValue;
		} catch (Exception ex) {
			logger.info("Exception occurred while Decrypting the Value {}", encryptedValue);
		}
		return "";

	}

}
