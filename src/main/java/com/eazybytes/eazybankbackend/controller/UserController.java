package com.eazybytes.eazybankbackend.controller;

import com.eazybytes.eazybankbackend.constants.ApplicationConstants;
import com.eazybytes.eazybankbackend.model.Customer;
import com.eazybytes.eazybankbackend.model.LoginRequestDTO;
import com.eazybytes.eazybankbackend.model.LoginResponseDTO;
import com.eazybytes.eazybankbackend.repository.CustomerRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserController {

    // Repository per operare sulla tabella customer
    private final CustomerRepository customerRepository;
    // Encoder password iniettato dal contesto security
    private final PasswordEncoder passwordEncoder;
    // AuthenticationManager usato per autenticare manualmente le credenziali inviate via API
    private final AuthenticationManager authenticationManager;
    // Environment fornisce accesso alle proprietà, qui usate per la secret del JWT
    private final Environment env;

    // Endpoint pubblico di registrazione utenti
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Customer customer) {
        try {
            // Codifica la password in ingresso usando l'encoder configurato
            String hashPwd = passwordEncoder.encode(customer.getPwd());
            customer.setPwd(hashPwd);
            // Imposta data di creazione
            customer.setCreateDt(new Date(System.currentTimeMillis()));
            // Persiste il nuovo utente
            Customer savedCustomer = customerRepository.save(customer);

            if (savedCustomer.getId() > 0) {
                // Inserimento riuscito -> 201 Created
                return ResponseEntity.status(HttpStatus.CREATED).
                        body("Given user details are successfully registered");
            } else {
                // Inserimento fallito -> 400 Bad Request
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                        body("User registration failed");
            }
        } catch (Exception ex) {
            // Gestione generica di errori imprevisti -> 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body("An exception occurred: " + ex.getMessage());
        }
    }

    // Endpoint protetto che restituisce il profilo dell'utente autenticato
    @RequestMapping("/user")
    public Customer getUserDetailsAfterLogin(Authentication authentication) {
        // Recupera i dettagli dell'utente loggato tramite principal (email)
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(authentication.getName());
        // Ritorna l'entità se presente, altrimenti null
        return optionalCustomer.orElse(null);
    }

    // Endpoint di login REST: autentica username/password e restituisce un JWT in header e body
    @PostMapping("/apiLogin")
    public ResponseEntity<LoginResponseDTO> apiLogin(@RequestBody LoginRequestDTO loginRequest) {
        String jwt = "";
        // Costruisce un token grezzo con le credenziali fornite dal client
        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(), loginRequest.password());

        // Delegando all'AuthenticationManager si attivano i provider configurati (DB, encoder, ecc.)
        Authentication authenticationResponse = authenticationManager.authenticate(authentication);

        if (null != authenticationResponse && authenticationResponse.isAuthenticated()) {
            if (null != env) {

                // Recupera la secret dal file di configurazione, con un fallback di default
                String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY, ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);

                // Genera la SecretKey a partire dalla stringa
                SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                // Costruisce il token JWT con issuer, claims e scadenza
                jwt = Jwts.builder()
                        .issuer("Eazy Bank")
                        .subject("JWT Token")
                        .claim("username", authenticationResponse.getName())
                        .claim("authorities", authenticationResponse
                                .getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.joining(",")))
                        .issuedAt(new Date())
                        .expiration(new Date((new Date()).getTime() + 30000000))
                        .signWith(secretKey).compact();
            }
        }
        // Risponde con 200, aggiungendo il JWT sia nell'header personalizzato sia nel body della response
        return ResponseEntity.status(HttpStatus.OK)
                .header(ApplicationConstants.JWT_HEADER, jwt)
                .body(new LoginResponseDTO(HttpStatus.OK.getReasonPhrase(), jwt));
    }
}
