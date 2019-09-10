package com.example.noteapp.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.noteapp.entity.DBFile;
import com.example.noteapp.repository.DBFileDao;

@Service
@Transactional(readOnly=true)
public class DBFileStorageService {

	@Autowired
	private DBFileDao dbFileDao;

	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	public DBFile storeFile(MultipartFile uploadRequest, String username, String restriction) throws Exception {
		
		String fileName = StringUtils.cleanPath(uploadRequest.getOriginalFilename());
		
		if(fileName.contains(".."))
			throw new Exception("FILENAME_CONTAINS_INVALID_PATH_SEQUENCE");
		
		DBFile dbFile = new DBFile();
		
//		dbFile.setFileId(1);
		dbFile.setUserName(username);
		dbFile.setFileName(fileName);
		dbFile.setFileType(uploadRequest.getContentType());
		dbFile.setData(uploadRequest.getBytes());
		dbFile.setRestricted(restriction);
		return dbFileDao.save(dbFile);
		
	}
	
	public DBFile getFile(Integer fileId) throws Exception {
		 Optional<DBFile> file = dbFileDao.findById(fileId);
		 if(!file.isPresent())
			 throw new Exception("FILE_IS_NOT_FOUND");
		return file.get();
	}
	
}
