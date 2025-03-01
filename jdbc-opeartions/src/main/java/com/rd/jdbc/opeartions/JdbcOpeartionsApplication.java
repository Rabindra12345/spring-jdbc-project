package com.rd.jdbc.opeartions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class JdbcOpeartionsApplication {

	public static void main(String[] args) {
		System.out.println("printing list ___________:"+ List.of("HELLO","world").toString());
		SpringApplication.run(JdbcOpeartionsApplication.class, args);
	}

}
