package com.nestoraluraoracleone.forohub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ForohubApplication {

	public static void main(String[] args) {

		SpringApplication.run(ForohubApplication.class, args);

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String rawPassword = "password123";
		String rawPassword2 = "password456";
		String encodedPassword = encoder.encode(rawPassword);
		String encodedPassword2 = encoder.encode(rawPassword2);
		System.out.println("Password encriptada password123 =  " + encodedPassword);
		System.out.println("Password encriptada2 password456 =  " + encodedPassword2);
		System.out.println("Coincide: " + encoder.matches(rawPassword, encodedPassword));
	}

}
