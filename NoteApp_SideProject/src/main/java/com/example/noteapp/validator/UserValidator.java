package com.example.noteapp.validator;


public class UserValidator {

	public static void validateUserForLogin (String email, String password) throws Exception {
		if( !validateUserEmail(email) )
			throw new Exception("CustomerValidator.INVALID_EMAIL_FORMAT");
		if( !validateUserPassword(password) )
			throw new Exception("CustomerValidator.INVALID_PASSWORD_FORMAT");
		
	}
	
	
	public static Boolean validateUserEmail (String email) {
		Boolean flag = false;
		if(email.matches("[a-zA-Z0-9_.]+@[a-z]+\\.[a-z]+"))
			flag = true;
		return flag;
	}
	
	public static Boolean validateUserPassword(String password) {
		Boolean flag = false;
		if (password.length()>=6 && password.length()<=20)
			flag = true;
		return flag;
	}
	
	
}
