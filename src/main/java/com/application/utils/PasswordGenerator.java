package com.application.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

        String password = "123456";
        String passwordEncoder = encoder.encode(password);

        System.out.println("La contraseña encriptada es: " + passwordEncoder);
    }
}