package com.eazybytes.eazybankbackend.config;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Metodo per convertire i claim del JWT
 */
public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    // Estrae i ruoli dal JWT di Keycloak e li trasforma in autorizzazioni Spring
    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {

        // Legge i claims del realm_access di KeyCloak in una serie di chiavi - valori
        Map<String, Object> realmAccess = (Map<String, Object>) source.getClaims().get("realm_access");

        // Se non ci sono ruoli nel token, ritorna lista vuota
        if (realmAccess == null || realmAccess.isEmpty()) {
            return new ArrayList<>();
        }

        // I ruoli vengono convertiti in ruoli leggibili per Spring
        Collection<GrantedAuthority> returnValue = ((List<String>) realmAccess.get("roles"))
                .stream()
                .map(roleName -> "ROLE_" + roleName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return returnValue;
    }
}
