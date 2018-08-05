package com.csomet.springboot.app.model.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileService implements IUploadFileService {

	public static final String UPLOAD_FOLDER = "uploads";
	
	@Override
	public Resource load(String filename) throws MalformedURLException {
		
		Path picturePath = getPath(filename);
		
		Resource rec = null;
		rec = new UrlResource(picturePath.toUri());
		
		if (!rec.exists() && !rec.isReadable()) {
			throw new RuntimeException("The resource " + picturePath.toString() + "can not be loaded");
		}
		
		return rec;
	}

	@Override
	public String copy(MultipartFile file) throws IOException {
		
		String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		Path rootPath   =  getPath(uniqueFileName);		
		Files.copy(file.getInputStream(), rootPath);
		
		return uniqueFileName;
	
	}

	@Override
	public boolean delete(String filename) {
	
		Path rootPicture = getPath(filename);
		File file = rootPicture.toFile();
		boolean result = false;
	
		if (file.exists() && file.canRead()) {
			if (file.delete()) {
				result = true;
			}
		}
		
		return result;
	}
	
	private Path getPath(String filename) {
		
		return Paths.get(UPLOAD_FOLDER).resolve(filename).toAbsolutePath();
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(Paths.get(UPLOAD_FOLDER).toFile());
		
	}

	@Override
	public void init() throws IOException {
		Files.createDirectory(Paths.get(UPLOAD_FOLDER));
		
	}

}
