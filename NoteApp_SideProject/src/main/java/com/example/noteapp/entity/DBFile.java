package com.example.noteapp.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name="files")
public class DBFile {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer file_id;
	private String filename;
	private String file_type;
	private String username;
	private String restricted;
	@Lob
	private byte[] data;
	
	
	public String getRestricted() {
		return restricted;
	}

	public void setRestricted(String restricted) {
		this.restricted = restricted;
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String userName) {
		this.username = userName;
	}

	public String getFileType() {
		return file_type;
	}

	public void setFileType(String fileType) {
		this.file_type = fileType;
	}


	public Integer getFileId() {
		return file_id;
	}
	public void setFileId(Integer fileId) {
		this.file_id = fileId;
	}
	public String getFileName() {
		return filename;
	}
	public void setFileName(String fileName) {
		this.filename = fileName;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	

}
