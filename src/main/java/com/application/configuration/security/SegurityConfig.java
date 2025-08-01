package com.application.configuration.security;

import com.application.usuario.services.UsuarioServices;
import com.application.utils.CustomUserDetails;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Data
@Configuration
public class SegurityConfig {

    @Autowired
    private final UsuarioServices usuarioServices;

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
                            .permitAll()
                    )
                    .exceptionHandling(ex -> ex
                            .accessDeniedPage("/error/403")
                    )
                    .build();
        } catch (Exception e) {
            throw new SecurityException("error al construir la cadena de seguridad", e);
        }
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}