package com.example.noteapp.api;

import java.util.List;
import java.util.Map;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.noteapp.entity.DBFile;
import com.example.noteapp.model.UploadFileResponse;
import com.example.noteapp.model.User;
import com.example.noteapp.model.UserFiles;
import com.example.noteapp.service.DBFileStorageService;
import com.example.noteapp.service.UserSvc;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path="/noteappApi") // This means URL's start with /noteappApi (after Application path)
public class NoteAppApi {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private DBFileStorageService dbFileStorageService;
	@Autowired
	private UserSvc userService;
	
 
    private static final Logger logger = LoggerFactory.getLogger(NoteAppApi.class);
    
    @RequestMapping(value="/upload/{username}/{restriction}", method=RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UploadFileResponse uploadFile (@RequestParam("file") MultipartFile file, @PathVariable("username") String username,  @PathVariable("restriction") String restriction) throws Exception {
    	
    	logger.info("File restriction is ::: " + restriction);
    	
    	DBFile dbFile = dbFileStorageService.storeFile(file, username, restriction);
    	String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
    			.path("/downloadFile/")
    			.path(dbFile.getFileId().toString())
    			.toUriString();
    	return new UploadFileResponse(dbFile.getFileName(), fileDownloadUri, file.getContentType(), file.getSize());
    }
	
	@RequestMapping(value="/signin", method=RequestMethod.POST)
	public ResponseEntity<User> authenticateUser (@RequestBody User userIn) throws Exception {
		User user = userService.signIn(userIn.getUsername(), userIn.getPassword());
		return new ResponseEntity<User> (user, HttpStatus.OK);
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public ResponseEntity<String> registerUser (@RequestBody User userIn) throws Exception {
//		User user = userService.authenticateUser(userIn.getId(), userIn.getPassword());
		String userPwd = userIn.getPassword();
		String encryptPwd = passwordEncoder.encode(userPwd);
		userIn.setPassword(encryptPwd);
		String msg = userService.register(userIn);
		return new ResponseEntity<String> (msg, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/downloadFile/{fileId}", method=RequestMethod.GET)
    public ResponseEntity<Resource> downloadFile(@PathVariable Integer fileId) throws Exception {
        // Load file from database
        DBFile dbFile = dbFileStorageService.getFile(fileId);

        logger.info("File from DB:::::" + dbFile.getFileName()+":"+dbFile.getFileType());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }
	
	
	@RequestMapping(value = "delete/{fileId}/{username:.+}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteNotes (@PathVariable("fileId") Integer noteId, @PathVariable("username") String username) throws Exception {
		 userService.deleteNotes(noteId, username);
		return new ResponseEntity<String>("Deleted", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getUserFiles/{username:.+}", method = RequestMethod.GET)
	public ResponseEntity<List<UserFiles>> getNotesByUserId(@PathVariable("username") String username) throws Exception {
		List<UserFiles> notes = userService.getUserFiles(username);
		return new ResponseEntity<List<UserFiles>> (notes, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAllFiles/{criteria}", method = RequestMethod.GET)
	public ResponseEntity<List<UserFiles>> getAllFiles(@PathVariable("criteria") String criteria){
		List<UserFiles> notes = userService.getAllFiles(criteria);
		return new ResponseEntity<List<UserFiles>> (notes, HttpStatus.OK);
	}

	@RequestMapping(value="/addfriend/{userId}/{friendId:.+}", method= RequestMethod.POST)
	public ResponseEntity<String> addFriend(@PathVariable("username") String username, @PathVariable("friendName") String friendName) throws Exception {
		String msg = userService.addFriend(username, friendName);
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}
}
