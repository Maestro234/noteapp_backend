package com.example.noteapp;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.noteapp.repository")
public class NoteAppSideProjectApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(NoteAppSideProjectApplication.class, args);
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		
	}
	
	

}
