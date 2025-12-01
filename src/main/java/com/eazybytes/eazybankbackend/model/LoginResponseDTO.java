package com.eazybytes.eazybankbackend.model;

/**
 * Payload di risposta per /apiLogin: include lo stato e il JWT generato.
 */
public record LoginResponseDTO(String status, String jwtToken) {
}
