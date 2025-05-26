package com.iss.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.iss.dao.Response;
import com.iss.service.UserImagesService;
import com.iss.util.EncryptedDecryptedObjectUtil;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
public class FaceLoginController {

	@Value("${DATE_TIME_FORMAT_EXPORTED_FILES}")
	private String dateTimeFormat;

	@Value("${SOMETHING_WENT_WRONG}")
	private String someThingWentWrong;

	@Value("${SECRET_KEY}")
	private String secretKey;

	@Value("${SECRET_IV}")
	private String secretIv;

	@Value("${IS_ENCRYPT_DECRYPT_ENABLE_DATABASE_DATA}")
	private boolean isEncryptDecryptDatabaseData;

	@Value("${IS_ENCRYPT_DECRYPT_REQUEST_RESPONSE_DATA}")
	private boolean isEncryptDecryptReqRespData;

	@Autowired
	private UserImagesService userImagesService;

	private static final Logger logger = LoggerFactory.getLogger(LoginUserController.class);

	@SuppressWarnings("unchecked")
	@PostMapping("/faceScan")
	public ResponseEntity<?> loginUsingFaceScan(@RequestParam("file") MultipartFile file) {
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		Map<String, Object> enRespObj = new LinkedHashMap<String, Object>();

		Response resp = new Response();
		try {
			resp = userImagesService.findUserByUserImage(file);

			if (Boolean.parseBoolean(resp.isSuccess())) {
				respObj.put("userId", resp.getUserId());
			}

			respObj.put("msg", resp.getMsg());
			respObj.put("userId", resp.getUserId());
			respObj.put("success", resp.isSuccess());

			logger.info("While Login using face scan - {}", resp.getMsg());

			return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.OK);

		} catch (Exception e) {
			respObj.put("message", someThingWentWrong);
			respObj.put("success", false);

			try {
				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);
			} catch (Exception e1) {
				logger.error("Error occured while encrypting the response- {}", e1);
			}

			logger.error("Exception occurred while Face loging In : {}", e);
			return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/downloadImage")
	public ResponseEntity<?> downloadImage() {
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();

		String saveDir = "D:/face-ing/images/newimages";
		String fileUrl = "https://blr1.digitaloceanspaces.com/faceingrecognize234/uploads/userprofile/1000000008.jpg";
		String fileName = "downloaded_image.jpg";

		try {
			// Create the directory if it doesn't exist
			Files.createDirectories(Paths.get(saveDir));

			// Open input stream from URL
			URL url = new URL(fileUrl);
			InputStream inputStream = url.openStream();

			// Define the target path
			String savePath = saveDir + "/" + fileName;

			// Copy input stream to local file
			Files.copy(inputStream, Paths.get(savePath), StandardCopyOption.REPLACE_EXISTING);

			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.OK);
	}

	@PostMapping("/saveUserImage/{userId}")
	public ResponseEntity<?> saveUserImages(@RequestParam("file") List<MultipartFile> imageList,
			@PathVariable String userId) {
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		Response resp = new Response();

		try {
			resp = userImagesService.saveUserImages(imageList, userId, dateTimeFormat);

			respObj.put("Msg", resp.getMsg());
			respObj.put("success", resp.isSuccess());

			return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.OK);

		} catch (Exception e) {
			respObj.put("Msg", resp.getMsg());
			respObj.put("success", resp.isSuccess());
			return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.BAD_REQUEST);
		}
	}

}
