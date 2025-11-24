# EazyBank – Spring Security Backend  
*Backend project developed while following the Udemy course “Spring Boot & Spring Security” by Madan Reddy (EazyBytes).*

## Overview  
This repository contains the **backend implementation** of the EazyBank application, built during the learning path of Madan Reddy’s (EazyBytes) Spring Security course.

The backend is the core focus of the course and includes the complete security workflow:  
- user authentication & authorization  
- password encoding  
- custom `UserDetailsService`  
- custom `AuthenticationProvider`  
- JWT generation, validation, and filters  
- secure REST endpoints based on roles and authorities  
- CSRF, CORS, and security configuration best practices

This project serves as a practical, code-driven exploration of Spring Security fundamentals and advanced concepts.

## Technologies  
- **Java 17+**  
- **Spring Boot**  
- **Spring Security**  
- **JWT**  
- **Spring Data JPA / Hibernate**  
- **MySQL**  

## Running the Application  
1. Clone the repository:  
   
        git clone https://github.com/DubStray/Spring-Security-Eazybank-BE
         

Configure database credentials in application.properties.

Run the Spring Boot application from your IDE or via:

        mvn spring-boot:run

--------------------------------------------------

# EazyBank – Backend Spring Security
*Progetto backend sviluppato seguendo il corso Udemy “Spring Boot & Spring Security” di Madan Reddy (EazyBytes).*

## Panoramica
Questa repository contiene l’implementazione backend dell’applicazione EazyBank, realizzata durante lo studio del corso di Spring Security di Madan Reddy.

Il backend è la parte centrale del percorso formativo e include tutta la logica di sicurezza:
- autenticazione e autorizzazione utenti
- gestione password e `UserDetailsService` personalizzato
- `AuthenticationProvider` custom
- generazione e validazione JWT
- filtri di sicurezza
- protezione degli endpoint REST in base ai ruoli
- configurazioni CORS, CSRF e best practice di Spring Security

Si tratta di un progetto pensato per imparare lavorando direttamente sulla logica di sicurezza reale.

## Tecnologie

- **Java 17+**
- **Spring Boot**
- **Spring Security**
- **JWT**
- **Spring Data JPA / Hibernate**
- **MySQL**

## Avvio dell’applicazione
1. Clona la repository:

       git clone https://github.com/DubStray/Spring-Security-Eazybank-BE

Configura le credenziali del database in application.properties.

Avvia l’app Spring Boot dall’IDE oppure con:

      mvn spring-boot:run
