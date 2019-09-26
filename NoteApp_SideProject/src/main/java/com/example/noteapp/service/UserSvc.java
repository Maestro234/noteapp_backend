package com.example.noteapp.service;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.noteapp.entity.DBFile;
import com.example.noteapp.entity.UserEntity;
import com.example.noteapp.model.User;
import com.example.noteapp.model.UserFiles;
import com.example.noteapp.repository.DBFileDao;
import com.example.noteapp.repository.UserDao;

@Service("userService")
@Transactional(readOnly = true) /** only for optimization **/
public class UserSvc {

	private static final Logger logger = LoggerFactory.getLogger(UserSvc.class);
	private final BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserDao userDao;
	@Autowired
	private DBFileDao dbFileDao;
	@Autowired
	public UserSvc(BCryptPasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public User signIn(String username, String password) throws Exception {

		System.out.println("In user service:::: username = " + username);
		UserEntity ue = userDao.findByUsername(username);
		logger.info("User fetched from DB:::::::::" + ue.getUsername() + ":" + ue.getEmailId() + ":" + ue.getId());
		if (ue == null)
			throw new Exception("INVALID_CREDENTIAL");

		User user = new User();
		user.setUsername(ue.getUsername());
		user.setEmail(ue.getEmailId());
		user.setId(ue.getId());
		user.setPassword(passwordEncoder.encode(ue.getPassword()));
		logger.info("Encoded password is :::: " + user.getPassword());

		return user;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String register(User user) throws Exception {

		UserEntity ue = userDao.findByUsername(user.getUsername());
		if (ue != null) {
			logger.info("username: " + user.getUsername() + " already exists");
			throw new Exception("USERNAME_ALREADY_EXISTS");
		}

		UserEntity toBePersisted = new UserEntity();
		toBePersisted.setEmailId(user.getEmail());
		toBePersisted.setPassword(user.getPassword());
		toBePersisted.setUsername(user.getUsername());
		toBePersisted.setPassword(passwordEncoder.encode(user.getPassword()));

		logger.info("user details to be persisted:" + user.getUsername() + ":" + user.getEmail() + ":"
				+ user.getPassword());

		userDao.save(toBePersisted);

		return "Account has been successfully created";
	}

	public List<UserFiles> getUserFiles(String username) {

		List<DBFile> dbFile = dbFileDao.findUserFiles(username);
		logger.info("Finished fetching files of type" + dbFile.get(0).getClass());
		List<UserFiles> returnList = new ArrayList<>();

		for (DBFile db : dbFile) {
			logger.info("file name::::: " + db.getFileName());
			UserFiles files = new UserFiles();
			files.setFileId(db.getFileId());
			files.setFilename(db.getFileName());
			files.setFiletype(db.getFileType());
			returnList.add(files);
		}
		return returnList;
	}
	
	
	public List<UserFiles> getAllFiles(String criteria) {

		List<DBFile> files = dbFileDao.searchFilesCriteria(criteria); 
		List<UserFiles> returnList = new ArrayList<>();

		for(DBFile db : files) {
			UserFiles uFiles = new UserFiles();
			uFiles.setFileId(db.getFileId());
			uFiles.setFilename(db.getFileName());
			uFiles.setFiletype(db.getFileType());
			returnList.add(uFiles);
		}
		logger.info("Number of files retrieved::::: " + returnList.size());
		return returnList;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String deleteNotes(Integer fileId, String username) {

		Optional<DBFile> file = dbFileDao.findById(fileId);

		if(!file.isPresent())
			return "File with " + fileId + " does not exist";

		dbFileDao.deleteFile(fileId, username);
		logger.info("Deleted file:::::::: " + fileId);

		return "File has been deleted";
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String addFriend(String username, String friend) {

		UserEntity friendFromDB = userDao.findByUsername(friend);

		if(friendFromDB != null){
			UserEntity userE = userDao.findByUsername(username);
			if (userE.getFriendIds().contains(friend))
				return "This user " + friend + " is already your friend";

			userE.getFriendIds().add(friend);
			userDao.saveAndFlush(userE);
		}
		return "Successfully added " + friend + " as a friend";
	}
}
