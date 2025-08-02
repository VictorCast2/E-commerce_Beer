package com.application.configuration.security;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Data
@Configuration
public class SegurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws SecurityException {
        try {
            return http
                    .csrf(AbstractHttpConfigurer::disable)
                    .sessionManagement(session -> session.
                            sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    )
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers(
                                    "/auth/**",         // Endpoints de autenticación
                                    "/usuario/**",      // Endpoints de usuario
                                    "/Assets/**",       // Recursos estáticos
                                    "/Js/**",
                                    "/Json/**",
                                    "/Css/**"
                            ).permitAll()
                            .anyRequest().authenticated()
                    )
                    .logout(logout -> logout
                            .logoutUrl("/auth/logout")
                            .logoutSuccessUrl("/auth/login?logout")
                            .clearAuthentication(true)
                            .invalidateHttpSession(true)
                            .deleteCookies("JSESSIONID", "access_token")
                            .permitAll()
                    )
                    .formLogin(form -> form
                            .loginPage("/auth/login")
                            .loginProcessingUrl("/auth/login")
                            .usernameParameter("correo")
                            .passwordParameter("password")
                            .defaultSuccessUrl("/", true)
                            .failureUrl("/auth/login")
                            .permitAll()
                    )
                    .exceptionHandling(ex -> ex
                            .accessDeniedPage("/error/403")
                    )
                    .headers(headers -> headers
                            // 🔐 Política de seguridad del contenido (Content Security Policy)
                            .contentSecurityPolicy(csp -> csp
                                    // Esta directiva limita el origen de recursos (scripts, imágenes, estilos, etc.) solo al mismo dominio ('self')
                                    .policyDirectives("default-src 'self'")
                            )
                            // 🛡️ Protección contra ataques de clickjacking
                            .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                            // Esto permite que la aplicación se muestre en un <iframe> SOLO si la petición proviene del mismo dominio
                            // Previene que otros sitios web maliciosos puedan embeber tu app en un iframe para robar datos
                            // 📅 HSTS (HTTP Strict Transport Security)
                            .httpStrictTransportSecurity(hsts -> hsts
                                            .includeSubDomains(true)
                                            // Aplica HSTS también a todos los subdominios (por ejemplo, admin.tuapp.com)
                                            .maxAgeInSeconds(31536000)
                                    // Fuerza que los navegadores accedan solo por HTTPS durante 1 año (31536000 segundos)
                            )
                    )
                    .httpBasic(Customizer.withDefaults())
                    .build();
        } catch (Exception e) {
            throw new SecurityException("Error al construir la cadena de seguridad", e);
        }
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}