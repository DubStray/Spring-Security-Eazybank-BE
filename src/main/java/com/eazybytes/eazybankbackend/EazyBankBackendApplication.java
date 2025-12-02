package com.eazybytes.eazybankbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true)
public class EazyBankBackendApplication {

	// Funzione di entrypoint: esegue il bootstrap dell'app Spring Boot
	public static void main(String[] args) {
		// Avvia l'app Spring Boot: crea il contesto, configura i bean e alza il server embedded
		SpringApplication.run(EazyBankBackendApplication.class, args);
	}

}
