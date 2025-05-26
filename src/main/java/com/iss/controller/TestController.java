package com.iss.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.iss.dao.EncryptedUIRequestDao;
import com.iss.dao.FileObjectDao;
import com.iss.dao.UserDao;
import com.iss.service.JsonWebTokenService;
import com.iss.service.TemporaryCacheService;
import com.iss.test.Employee;
import com.iss.util.DecryptUIRequestUtil;
import com.iss.util.EncryptedDecryptedObjectUtil;
import com.iss.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class TestController {
	
	public static final String SECRET_KEY = "vinayakISS123456";
	public static final String SECRET_IV = "vinayak123456ISS";
	
	@Value("${PROFILE_IMAGE_LOCAL_PATH}")
	private String imagePath;
	
	@Value("${KEY_ENCRYPTION_KEY}")
    private String keyEnKey;
	
	@Value("${ENCRYPT_ALGO}")
	private String encryptAlgo;
	
	@Value("${SECRET_KEY_FACTORY}")
	private String secretKeyFactory;
	
	@Autowired
	private JsonWebTokenService jwtService;
	
	@Autowired
	private TemporaryCacheService temporaryCacheService;

	private final JwtUtil jwtUtil;
	
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    public TestController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
	
	@PostMapping("/loginJWT")
    public ResponseEntity<?> login(@RequestBody UserDao dao,HttpServletRequest request) throws Exception {
		logger.info("Testing start");
		try {
			throw new ArithmeticException();
		} catch (Exception e) {
			logger.info("Adding guest to user :{} - {}", e);
		}
		logger.info("Testing end");
//		SecretKey key=EncryptDecryptUtil.aesKey(keyEnKey);
		
//		temporaryCacheService.put("name", "Vinayak Bhilawadi");
//		
//		String fName=temporaryCacheService.get("name");
//		System.out.println("Cached name is : "+fName);
//		
//		temporaryCacheService.remove("name");
//		
//		String fName1=temporaryCacheService.get("name");
//		System.out.println("Cached name is after removal : "+fName1);
//		
//		
//		Employee emp=new Employee("1001", "Vinayak","India","Bagalkot","10");
//		System.out.println("Befire Encryption : "+emp);
//////		
//		Employee emp1=(Employee) EncryptedDecryptedObjectUtil.getEncryptedObject(emp, encryptAlgo, secretKeyFactory,true);
//		System.out.println("After Encryption : "+emp1);
////		
//		Employee emp2=(Employee) EncryptedDecryptedObjectUtil.getDecryptedObject(emp1,encryptAlgo, secretKeyFactory);
//		System.out.println("After Decryption : "+emp2);
		
//		String name ="India";
//		System.out.println("Before Encryption : "+name);
//		
//		String name1=(String) EncryptedDecryptedObject.getEncryptedString(name, key);
//		System.out.println("After Encryption : "+name1);
//		
//		String name2=(String) EncryptedDecryptedObject.getDecryptedString(name1, key);
//		System.out.println("After Decryption : "+name2);
//		
//		System.out.println("Key is : "+EncryptDecryptUtil.aesKey(keyEnKey));
//		String ip=request.getRemoteAddr();
//		
//		System.out.println("Address is : "+ip);
		
//		System.out.println("This is Login method");
		
//		String name="Vinayak";
//		System.out.println("Name before encryption : "+name);
////		
//		String name1="";
//		String name2="";
//		 name1=(String) EncryptedDecryptedObject.getEncryptedString(name, encryptAlgo, secretKeyFactory);
//		 System.out.println("Name after encryption : "+name1);
//		 name2=(String) EncryptedDecryptedObject.getDecryptedString(name1, encryptAlgo, secretKeyFactory);
//		 
//		 System.out.println("Name after decryption : "+name2);
//			name1 = EncryptDecryptData.decrypt(name, encryptAlgo, secretKeyFactory);
		
//		String str="";
//		Integer num1=10;
//		System.out.println("Num value before encryption : "+num1);
//		str=(String) EncryptedDecryptedObject.getEncryptedString(num1, encryptAlgo, secretKeyFactory);
//		System.out.println("Num after encryption : "+str);
		
		
//        String username = request.get("username");
//        String password = request.get("password");
//		 System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

//        if ("admin".equals(username) && "password".equals(password)) { // Mock authentication
//        if(true) {
//        	dao.setUserId("1234567890");
//            String token = jwtUtil.generateToken(dao.getUserId(),new Long(10));
//            jwtService.createUSerWithToken("", token, token, "date",new Long(10),new Long(10));
//            
//            return ResponseEntity.ok(Map.of("token", token));
//        } else {
//            return ResponseEntity.status(401).body("Invalid credentials");
//        }
		 
		  return ResponseEntity.ok(Map.of("token", ""));
    }
	
	 @GetMapping("/hello")
	    public String hello() {
	        return "Hello, Authenticated User!";
	    }
	 
	 @PostMapping("/getFormData")
	 public void getFormdata(@RequestParam(value="userName")String emailId)
	 {
		 System.out.println("User Name : "+emailId);
	 }
	 
	 @PostMapping("/storeFile")
	 public void storeFile(@RequestPart("file") MultipartFile fileName)
	 {
		 FileObjectDao dao=new FileObjectDao();
		 
//		 dao=CreateProfileImagesUtil.storeFile(fileName, imagePath);
		 System.out.println("Status : "+dao.isSuccess());
		 System.out.println("Image Path : "+dao.getProfileImagePath());
	 }
//		 String UPLOAD_DIR = "D://images/uploads/";
//		 try {
//	            // Create the upload directory if it doesn't exist
//	            Path uploadPath = Paths.get(UPLOAD_DIR);
//	            if (!Files.exists(uploadPath)) {
//	                Files.createDirectories(uploadPath);
//	            }
//
//	            // Save the file
//	            String originalFilename = fileName.getOriginalFilename();
//	            Path filePath = uploadPath.resolve(originalFilename);
//	            fileName.transferTo(filePath.toFile());
//
//	          System.out.println("File uploaded successfully: " + originalFilename);
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	           System.out.println("File upload failed: " + e.getMessage());
//	        }
//	    }
	 
	 @PostMapping("/testEn")
	    public ResponseEntity<?> login(@RequestBody EncryptedUIRequestDao dao) throws Exception {
		 
//		 System.out.println("Dao is : "+dao);
//		 
//		 UserDao userDao= (UserDao) DecryptUIRequestUtil.getDecryptedDataFromUIRequest(dao.getEnData(),UserDao.class, SECRET_KEY, SECRET_IV, true);
//		 
//		 System.out.println("User Dao is : "+userDao);
		 
		 String name="Vinayak";
		 
//		 String enName=EncryptedDecryptedObjectUtil.getEncryptedString(name, name, name, false)
		 
			return null;
		 
	 }
			
		 
		 
}
