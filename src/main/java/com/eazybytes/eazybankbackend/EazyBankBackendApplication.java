package com.eazybytes.eazybankbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EazyBankBackendApplication {

	// Funzione di entrypoint: esegue il bootstrap dell'app Spring Boot
	public static void main(String[] args) {
		// Avvia l'app Spring Boot: crea il contesto, configura i bean e alza il server embedded
		SpringApplication.run(EazyBankBackendApplication.class, args);
	}

}
