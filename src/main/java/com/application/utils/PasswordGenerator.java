package com.application.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String password = "12345";
        String passwordEncoder = encoder.encode(password);

        System.out.println("La contraseña encriptada es: " + passwordEncoder);
    }
}