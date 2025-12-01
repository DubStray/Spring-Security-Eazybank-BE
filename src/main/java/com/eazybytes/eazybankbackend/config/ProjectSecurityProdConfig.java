package com.eazybytes.eazybankbackend.config;

import com.eazybytes.eazybankbackend.exceptionhandling.CustomAccessDeniedHandler;
import com.eazybytes.eazybankbackend.exceptionhandling.CustomBasicAuthenticationEntryPoint;
import com.eazybytes.eazybankbackend.filter.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Profile("prod")
public class ProjectSecurityProdConfig {

    /*
    lambda -> anyRequest().permiAll() -> Rende accessibile qualsiasi API.
    lambda -> anyRequest().denyAll() -> Rende inaccessibile tutte le API.
    lambda -> request.requestMatchers("/API/") -> rende accessibile o inaccessibile l'API definita nel request matcher
    request.requestMatchers("/API").authenticated -> rende accessibile l'API solo se si é autenticati
    request.requestMatchers("/API").permitAll -> rende accessibile l'API pubblicamente
     */
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        // Gestore per spostare il token CSRF in un attributo della request
        CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();
        http
                // Configurazione stateless: nessuna sessione mantenuta lato server
                .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // CORS in produzione: consente solo l'origin https locale (va adeguato all'ambiente reale)
                .cors(corsConfig -> corsConfig.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("https://localhost:4200"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setExposedHeaders(Arrays.asList("Authorization"));
                        config.setMaxAge(3600L);
                        return config;
                    }
                }))
                // CSRF attivo con cookie leggibile dal client, esclusi alcuni endpoint pubblici
                .csrf(csrfConfig -> csrfConfig.csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
                        .ignoringRequestMatchers( "/contact","/register", "/apiLogin")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                // Filtri custom attorno a BasicAuthenticationFilter
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new RequestValidationBeforeFIlter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new AuthoritiesLogginAfterFilter(), BasicAuthenticationFilter.class)
                .addFilterAt(new AuthoritiesLogginAtFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
                // In prod richiede HTTPS (requiresSecure). Migliore pratica per ambienti pubblici.
                .requiresChannel(rcc -> rcc.anyRequest().requiresSecure()) // Only HTTPS
                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/myAccount").hasAuthority("VIEWACCOUNT")
//                        .requestMatchers("/myBalance").hasAnyAuthority("VIEWBALANCE", "VIEWACCOUNT")
//                        .requestMatchers("/myLoans").hasAuthority("VIEWLOANS")
//                        .requestMatchers("/myCards").hasAuthority("VIEWCARDS")
                        .requestMatchers("/myAccount").hasRole("USER")
                        .requestMatchers("/myBalance").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/myLoans").hasRole("USER")
                        .requestMatchers("/myCards").hasRole("USER")
                        .requestMatchers("/user").authenticated()
                        .requestMatchers("/notices", "/contact", "/error", "/register", "/invalidSession", "/apiLogin").permitAll());
        // Form login di default
        http.formLogin(withDefaults());
        // Basic auth con entry point custom
        http.httpBasic(hbc -> hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
        // Handler custom per accesso negato
        http.exceptionHandling(ehc -> ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));
        return http.build();
    }

    // AuthenticationManager per profilo prod basato sul provider custom collegato al DB
    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        EazyBankProdUsernamePwdAuthenticationProvider authenticationProvider = new EazyBankProdUsernamePwdAuthenticationProvider(userDetailsService, passwordEncoder);
        ProviderManager providerManager = new ProviderManager(authenticationProvider);

        // Non rimuove le credenziali post-autenticazione, utili per filtri o log
        providerManager.setEraseCredentialsAfterAuthentication(false);
        return providerManager;
    }

    /*
    Metodo di encoder per la password, di default si dovrebbe utilizzare l'oggetto BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt é di default il metodo migliore per criptare password
//      return new BCryptPasswordEncoder();

        // Tramite costruttore di PasswordEncoderFactories c'é la possibilitá di non seguire lo standard di encrypt per
        // le password (BCrypt). Tuttavia lo si deve specificare {noop} per il plain text, {bcrypt} per bcrypt, etc...
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /*
    API di IHaveBeenPwnd? per testare la durevolezza di una password e capire se é compromessa o meno
     */
    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        // Checker che interroga il servizio Have I Been Pwned
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }
}
