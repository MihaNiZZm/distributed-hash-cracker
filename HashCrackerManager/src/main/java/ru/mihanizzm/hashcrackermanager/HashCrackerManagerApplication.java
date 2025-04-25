package ru.mihanizzm.hashcrackermanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HashCrackerManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HashCrackerManagerApplication.class, args);
	}

}
