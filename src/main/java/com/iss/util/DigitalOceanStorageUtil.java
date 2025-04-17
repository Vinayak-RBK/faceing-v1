package com.iss.util;

import java.io.File;
import java.net.URI;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

public class DigitalOceanStorageUtil {
	
	 private static final String ENDPOINT = "https://blr1.digitaloceanspaces.com"; // Replace region if needed
	    private static final String ACCESS_KEY = "DO00A2L2YJPCH3WZHD3P";
	    private static final String SECRET_KEY = "CE2lCWP0ieji88qYAQo0pFNT8PnNMp9RzmKrCox+9uY";
	    private static final String BUCKET_NAME = "faceingrecognize234";

	    public static String uploadImage(String filePath, String objectKey) {
	    	String imageUrl="";
	        S3Client s3 = S3Client.builder()
	            .endpointOverride(URI.create(ENDPOINT))
	            .credentialsProvider(StaticCredentialsProvider.create(
	                AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY)))
	            .region(Region.US_EAST_1) // region is ignored but required
	            .build();

	        try {
	            PutObjectRequest putRequest = PutObjectRequest.builder()
	                .bucket(BUCKET_NAME)
	                .key(objectKey)
	                .acl("public-read") // make it public if you want public link
	                .build();

	            s3.putObject(putRequest, new File(filePath).toPath());

	             imageUrl = ENDPOINT + "/" + BUCKET_NAME + "/" + objectKey;
	        } catch (S3Exception e) {
	            e.printStackTrace();
	        } finally {
	            s3.close();
	        }
			return imageUrl;
	    }

}
