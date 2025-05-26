package com.iss.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.iss.controller.AddGuestController;
//import com.iss.test.Person;

public class DecryptUIRequestUtil {

	public static final String SECRET_KEY = "vinayakISS123456";
	public static final String SECRET_IV = "vinayak123456ISS";

	private static final Logger logger = LoggerFactory.getLogger(DecryptUIRequestUtil.class);

//	public static void main(String[] args) throws Exception {
//
//		Gson gson = new Gson();
//		Person per = new Person("Vinayak", 30);
//		String jsonData = gson.toJson(per);
//		System.out.println("String data : " + jsonData);
//		String enData = (String) EncryptedDecryptedObjectUtil.getEncryptedString(jsonData, SECRET_KEY, SECRET_IV, true);
//		System.out.println("Encrypted Data is : " + enData);
//
//		Person decPer = (Person) getDecryptedDataFromUIRequest(jsonData, Person.class, SECRET_KEY, SECRET_IV, false);
//
//		System.out.println("Decrypted Data is : " + decPer);
//	}

	public static Object getDecryptedDataFromUIRequest(String enData, Class<?> className, String secretKey,
			String secretIV, boolean isEncryptDecryptReqRespData) {

		logger.info("Start of getDecryptedDataFromUIRequest method ");
		String decData = "";
		Gson gson = new Gson();

		logger.info("Encryption/Decryption is for UI : {} ", isEncryptDecryptReqRespData);

		try {
			if (isEncryptDecryptReqRespData) {
				decData = (String) EncryptedDecryptedObjectUtil.getDecryptedString(enData, secretKey, secretIV,
						isEncryptDecryptReqRespData);
				logger.info("Decrypted String Data : {} ", decData);
				Object obj = gson.fromJson(decData, className);
				logger.info("Decrypted Object Data : {} ", obj);
				return obj;
			} else {
				Object obj = gson.fromJson(enData, className);
				return obj;
			}
		} catch (Exception e) {
			logger.info("Exception is occured during decryption of {}", enData);
		}
		logger.info("End of getDecryptedDataFromUIRequest method ");

		return null;

	}

}
