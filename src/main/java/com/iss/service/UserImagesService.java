package com.iss.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.iss.dao.Response;

@Service
public interface UserImagesService {
	
	public void deleteUserImagesFromLocal();
	
	public Response saveUserImages(List<MultipartFile> imageList, String userId,String dateTimeFormat);
	
	public Response findUserByUserImage(MultipartFile file);
	
}
