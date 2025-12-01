package com.eazybytes.eazybankbackend.model;

/**
 * Payload in ingresso per /apiLogin: trasporta username e password inviati dal client.
 */
public record LoginRequestDTO(String username, String password) {
}
