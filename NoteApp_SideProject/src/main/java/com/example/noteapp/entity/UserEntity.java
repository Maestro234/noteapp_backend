package com.example.noteapp.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="users")
public class UserEntity {
	
	@Id
	private Integer user_id;
	private String username;
	@Column(name="user_email_id")
	private String emailId;
	private String password;
//	@Lob
//	private byte[] data;
//	@OneToMany(cascade=CascadeType.ALL)
//	@JoinTable(name="user_notes",
//	joinColumns=@JoinColumn(name="username", referencedColumnName="username"),
//	inverseJoinColumns=@JoinColumn(name="note_id", referencedColumnName="note_id", unique=true))
//	private List<Note> notes;
	@ElementCollection
	private List<String> friendIds;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getFriendIds() {
		return friendIds;
	}
	public void setFriendIds(List<String> friendIds) {
		this.friendIds = friendIds;
	}
	public Integer getId() {
		return user_id;
	}
	public void setId(Integer id) {
		this.user_id = id;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

}
