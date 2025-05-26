package com.iss.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.multipart.MultipartFile;

import com.iss.dao.FileObjectDao;

public class CreateProfileImagesUtil {

	public static FileObjectDao storeFile(MultipartFile fileName, String profileImagePath) {
		FileObjectDao dao=new FileObjectDao();
//		String UPLOAD_DIR = "D://images/uploads/";
		try {
			// Create the upload directory if it doesn't exist
			Path uploadPath = Paths.get(profileImagePath);
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			// Save the file
			String originalFilename = fileName.getOriginalFilename();
			Path filePath = uploadPath.resolve(originalFilename);
			fileName.transferTo(filePath.toFile());

			System.out.println("File uploaded successfully: " + originalFilename);
			dao.setProfileImagePath(profileImagePath+originalFilename);
			dao.setSuccess(true);
			return dao;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("File upload failed: " + e.getMessage());
			dao.setSuccess(false);
			return dao;
		}
	}

}
