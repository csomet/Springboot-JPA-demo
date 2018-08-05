package com.csomet.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.csomet.springboot.app.model.services.IUploadFileService;

@SpringBootApplication
public class SpringBootJpaApplication implements CommandLineRunner{

	@Autowired
	IUploadFileService uploadService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		uploadService.deleteAll();
		uploadService.init();
		
		
		String pass = "1234";
		
		for (int i=0; i< 2; i++) {
			String encPass = passwordEncoder.encode(pass);
			System.out.println("Pass[" + (i+1) + "]: " + encPass);
		
		}
	}
}
