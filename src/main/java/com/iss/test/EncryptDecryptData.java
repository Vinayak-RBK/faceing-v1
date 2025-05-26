package com.iss.test;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import com.iss.dao.Response;

public class EncryptDecryptData {
	
	// Generate AES Secret Key
    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256); // 128, 192, or 256-bit key
        return keyGenerator.generateKey();
    }

    // Encrypt Data
    public static Object encrypt(byte[] data, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(data);
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Decrypt Data
    public static Object decrypt(String encryptedData, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedBytes);
    }
    
    public static void main(String[] args) throws Exception {
        SecretKey secretKey = generateKey();
        
        String originalData = "Hello, Secure World!";
        
        byte[] strArray = ((String) originalData).getBytes(StandardCharsets.UTF_8);
        String encryptedData = (String) encrypt(strArray, secretKey);
        String decryptedData = (String) decrypt(encryptedData, secretKey);

        System.out.println("Original Data: " + originalData);
        System.out.println("Encrypted Data: " + encryptedData);
        System.out.println("Decrypted Data: " + decryptedData);
        
//        printNo(10);
//        printNo("Vinayak");
//        Response resp=new Response();
//        resp.setMsg("This is Vinayak");
//        resp.setSuccess(true);
//        
//        Student st=new Student("1001","India","Bagalkot","karnataka");
//        
//        printNo(st);
    }
    
    public static void printNo(Object obj) throws Exception
    {
    	System.out.println(("Obj is : "+obj.getClass().getSimpleName()));
    	 Class<?> clazz = obj.getClass();
    	Field[] fields = clazz .getDeclaredFields();
    	
    	System.out.println("Fields of class: " + clazz.getSimpleName());
        for (Field field : fields) {
        	String fieldName=field.getName();
            
            Object value=getFieldValue(obj, fieldName);
            
            System.out.println("Feild name is : "+fieldName+" Value is : "+value);
            field.setAccessible(true);
            field.set(obj, "Vinayak");
        }
        
        for (Field field : fields) {
        	String fieldName=field.getName();
            
            Field fieldValue = obj.getClass().getDeclaredField(fieldName);
            System.out.println(fieldValue.getType());
        }
    }
    
    public static Object getFieldValue(Object obj, String fieldName) throws Exception {
        Class<?> clazz = obj.getClass();
        Field field = clazz.getDeclaredField(fieldName);   // Get field by name
        field.setAccessible(true);                         // Make the private field accessible
        return field.get(obj);                             // Get the value of the field
    }

}
