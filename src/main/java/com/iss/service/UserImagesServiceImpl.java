package com.iss.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.iss.dao.FileObjectDao;
import com.iss.dao.Response;
import com.iss.entity.EndUser;
import com.iss.entity.UserImages;
import com.iss.repository.LoginUserRespository;
import com.iss.repository.UserImagesRepository;
import com.iss.util.CreateProfileImagesUtil;
import com.iss.util.DigitalOceanStorageUtil;

@Service
public class UserImagesServiceImpl implements UserImagesService {

	@Value("${PROFILE_IMAGE_LOCAL_PATH}")
	private String imagePath;

	@Autowired
	private LoginUserRespository loginUserRespository;

	@Autowired
	private UserImagesRepository userImagesRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(UserImagesServiceImpl.class);
	
	@Override
	public void deleteUserImagesFromLocal() {

		File folder = new File(imagePath);

		if (folder.exists() && folder.isDirectory()) {
			File[] files = folder.listFiles();

			if (files != null && files.length > 0) {
				for (File file : files) {
					if (file.isFile()) {
						if (file.delete()) {
						} else {
							// nothing to do
						}
					}
				}
			} else {
				// nothing to do
			}
		} else {
			// nothing to do
		}
	}

	@Override
	public Response saveUserImages(List<MultipartFile> imageList, String userId, String dateTimeFormat) {
		Response resp = new Response();
		FileObjectDao fileObjDao = new FileObjectDao();
		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);

		Optional<EndUser> user = loginUserRespository.findById(userId);

		if (user.isEmpty()) {
			resp.setMsg("Invalid user Id is received");
			resp.setSuccess(Boolean.toString(false));
			
			logger.info("While saving user images in saveUserImages() method - {}",resp.getMsg());
			return resp;
		} else {
			UserImages userImages = null;

			for (MultipartFile file : imageList) {
				String currentDate = sdf.format(new Date());
				// creating folder and storing in the given image path
				fileObjDao = CreateProfileImagesUtil.storeFile(file, imagePath);

				// storing image in bucket and getting link and storing that in db image column
				String imagePath = DigitalOceanStorageUtil.uploadImage(fileObjDao.getProfileImagePath(),
						"uploads/userprofile/" + userId + "_" + currentDate + ".jpg");

				userImages = new UserImages();
				userImages.setImagePath(imagePath);
				userImages.setEndUser(user.get());

				userImagesRepository.save(userImages);

			}

			deleteUserImagesFromLocal();
			
			resp.setMsg("Images are uploaded Successfully...");
			resp.setSuccess(Boolean.toString(true));
		}
		
		logger.info("While saving user images in saveUserImages() method - {}",resp.getMsg());
		return resp;
	}

	@Override
	public Response findUserByUserImage(MultipartFile file) {
		String userRecievedImagesPath = "D:/face-ing/images/receivedImages"; // Change this to your desired path
		String userStoredImagesPath = "D:/face-ing/images/storedImages";
		InputStream inputStream = null;
		File deleteReceivedFile = null;
		File deleteStoredFile = null;
		List<UserImages> userImages=new ArrayList<UserImages>();

		double res = 0;
		boolean isUser = false;

		Response resp = new Response();
		if (file.isEmpty()) {
			resp.setMsg("Image is not recevied for face scan.");
			resp.setSuccess(Boolean.toString(false));
			
			logger.info("While finding user by user images in findUserByUserImage() method - {}",resp.getMsg());
			return resp;
		}

		try {
			// Create the upload directory if it doesn't exist
			File receivedImageUploadDir = new File(userRecievedImagesPath);
			if (!receivedImageUploadDir.exists()) {
				receivedImageUploadDir.mkdirs();
			}
			
			File storedImageUploadDir = new File(userStoredImagesPath);
			if (!storedImageUploadDir.exists()) {
				storedImageUploadDir.mkdirs();
			}

			// Build the full path for the file
			String filePath = userRecievedImagesPath + File.separator + file.getOriginalFilename();

			// Save the received image file from UI to local folder
			file.transferTo(new File(filePath));

			 userImages = userImagesRepository.findAll();

			if (userImages.isEmpty()) {
				resp.setMsg("No User Images are found");
				resp.setSuccess(Boolean.toString(false));
				
				logger.info("While finding user by user images in findUserByUserImage() method - {}",resp.getMsg());
				return resp;
			} else {
				for (UserImages images : userImages) {
					URL url = new URL(images.getImagePath());
					inputStream = url.openStream();

					// Define the target path
					String savePath = userStoredImagesPath + "/" + images.getImageId() + ".jpg";

					// Copy input stream to local file
					Files.copy(inputStream, Paths.get(savePath), StandardCopyOption.REPLACE_EXISTING);

					deleteReceivedFile = new File(filePath);

					deleteStoredFile = new File(savePath);

					res = compareHistograms(filePath, savePath);

					if (res * 100 > 80) {
						isUser = true;
						break;
					}

					if (deleteStoredFile.exists()) {
						deleteStoredFile.delete();
					}

				}

//				inputStream.close();
				if (isUser) {
					resp.setMsg("Login Successful..");
					resp.setSuccess(Boolean.toString(true));
					resp.setUserId(userImages.get(0).getEndUser().getUserId().toString());
				} else {
					resp.setMsg("User not found.");
					resp.setSuccess(Boolean.toString(false));
				}
				
				logger.info("While finding user by user images in findUserByUserImage() method - {}",resp.getMsg());
				return resp;
			}

		} catch (IOException e) {
			System.out.println(e);
			resp.setMsg("Some Thing went wrong");
			resp.setSuccess(Boolean.toString(false));
			
			logger.error("Exception occured While finding user by user images in findUserByUserImage() method - {}",resp.getMsg());
			return resp;
		} finally {
			try {

				if (deleteReceivedFile.exists()) {
					deleteReceivedFile.delete();
				}

				if (deleteStoredFile.exists()) {
					deleteStoredFile.delete();
				}

				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private double compareHistograms(String imagePath1, String imagePath2) {
		System.load("C:\\Users\\bvina\\Downloads\\opencv\\build\\java\\x64\\opencv_java4110.dll");
		Mat img1 = Imgcodecs.imread(imagePath1);
		Mat img2 = Imgcodecs.imread(imagePath2);

		if (img1.empty() || img2.empty()) {
			throw new RuntimeException("Error: Cannot load images.");
		}

		Mat hsv1 = new Mat(), hsv2 = new Mat();
		Imgproc.cvtColor(img1, hsv1, Imgproc.COLOR_BGR2HSV);
		Imgproc.cvtColor(img2, hsv2, Imgproc.COLOR_BGR2HSV);

		Mat hist1 = new Mat(), hist2 = new Mat();
		Imgproc.calcHist(java.util.Collections.singletonList(hsv1), new MatOfInt(0), new Mat(), hist1,
				new MatOfInt(256), new MatOfFloat(0, 256));
		Imgproc.calcHist(java.util.Collections.singletonList(hsv2), new MatOfInt(0), new Mat(), hist2,
				new MatOfInt(256), new MatOfFloat(0, 256));

		Core.normalize(hist1, hist1, 0, 1, Core.NORM_MINMAX);
		Core.normalize(hist2, hist2, 0, 1, Core.NORM_MINMAX);

		return Imgproc.compareHist(hist1, hist2, Imgproc.CV_COMP_CORREL); // Closer to 1 = More similar
	}

}
