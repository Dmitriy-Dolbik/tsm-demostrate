package ru.karod.tsm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class TsmApplication {

	public static void main(String[] args) {
		SpringApplication.run(TsmApplication.class, args);
	}

}
