package com.iss.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.iss.util.DigitalOceanStorageUtil;

@Service
public class MultipartFileCacheService {

	private String image = "";

	private final String CACHE_DIR = System.getProperty("java.io.tmpdir") + File.separator + "cached_uploads";

	public MultipartFileCacheService() {
		File dir = new File(CACHE_DIR);
		if (!dir.exists()) {
			dir.mkdirs();
		}

	}

	@CacheEvict(value = "fileCache", key = "#fileName")
	public void deleteCachedFile(String fileName) throws IOException {
		Path filePath = Path.of(CACHE_DIR, fileName);
		Files.deleteIfExists(filePath);
	}

	@Cacheable(value = "fileCache", key = "#fileName")
	public File cacheFile(String fileName, MultipartFile multipartFile) throws IOException {
		File file = null;
		Path filePath = Path.of(CACHE_DIR, fileName);
		Files.copy(multipartFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

		// storing image in bucket and getting link and storing that in db image column
		image = DigitalOceanStorageUtil.uploadImage(filePath.toAbsolutePath().toString(),
				"uploads/userprofile/" + multipartFile.getOriginalFilename());

		file = new File(image);

		return file;
	}

}
