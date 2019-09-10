package com.example.noteapp.model;

public class UploadFileResponse {

	private String fileName;
	private String downloadUri;
	private String fileType;
	private long size;
	
	
	public UploadFileResponse(String fileName, String downloadUri, String fileType, long size) {
		super();
		this.fileName = fileName;
		this.downloadUri = downloadUri;
		this.fileType = fileType;
		this.size = size;
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getDownloadUri() {
		return downloadUri;
	}
	public void setDownloadUri(String downloadUri) {
		this.downloadUri = downloadUri;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	
	
}
