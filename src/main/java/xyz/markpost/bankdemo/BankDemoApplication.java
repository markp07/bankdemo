package xyz.markpost.bankdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * TODO: Client uitbreiden - validatie toevoegen
 * TODO: Account entity toevoegen -> overboeking mag niet negatief
 * TODO: unit tests maken
 * TODO: swagger toevoegen
 * TODO: migrate to h2 database
 */
@SpringBootApplication
public class BankDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankDemoApplication.class, args);
	}

}

