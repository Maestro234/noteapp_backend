package com.example.noteapp.model;

import org.springframework.web.multipart.MultipartFile;

public class UploadFileRequest {
	
	private String fileName;
	private String userName;
	private MultipartFile file;
	
	

	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	

}
