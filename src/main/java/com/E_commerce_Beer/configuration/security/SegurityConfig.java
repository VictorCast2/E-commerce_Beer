package com.E_commerce_Beer.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SegurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws SecurityException {
        try {
            return http
                .csrf(csrf -> csrf.disable())
                // Configura la sesión como sin estado (stateless)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                    // Permite el acceso a los endpoints de autenticación
                    .requestMatchers("/auth/**").permitAll()
                    // Permite el acceso a los recursos estáticos
                    .requestMatchers("/", "/Assets/**", "/Js/**", "/Css/**").permitAll()
                    // Requiere autenticación para cualquier otra petición
                    .anyRequest().authenticated()
                )
                .logout(logout -> logout
                    .logoutUrl("/auth/logout")
                    .logoutSuccessUrl("/auth/login?logout")
                    .permitAll()
                )
                .formLogin(form -> form
                    .loginPage("/auth/login")
                    .permitAll()
                )
                .exceptionHandling(ex -> ex
                    .accessDeniedPage("/error/403")
                )
                .build();
        } catch (Exception e) {
            throw new SecurityException("Error al construir la cadena de seguridad", e);
        }
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}