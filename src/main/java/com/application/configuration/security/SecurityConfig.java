package com.application.configuration.security;

import com.application.service.implementation.usuario.UsuarioServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .invalidSessionUrl("/auth/login")
                        .maximumSessions(2)
                        .expiredUrl("/auth/login?expired")
                        .sessionRegistry(sessionRegistry())
                )
                .authorizeHttpRequests(auth -> auth
                        // Configurar endpoints privados
                        /* ----- Admin ----- */
                        .requestMatchers("/admin/categoria/**").hasRole("ADMIN")
                        .requestMatchers("/admin/producto/**").hasRole("ADMIN")

                        // Configurar endpoints públicos (sin autenticación)
                        .requestMatchers(
                                "/auth/**",
                                "/usuario/**",
                                "/producto/**",
                                "/error/**",
                                "/auth/logout",
                                "/error/"
                        ).permitAll()

                        // Configurar endpoints públicos estáticos (sin autenticación)
                        .requestMatchers("/", "/Assets/**", "/Js/**", "/Css/**").permitAll()

                        // Configurar endpoints NO ESPECIFICADOS
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login")
                        .failureUrl("/auth/login?error=true")
                        .permitAll()
                )
//                .oauth2Login(oauth2 -> oauth2
//                        .loginPage("/auth/login")
//                        .userInfoEndpoint(userInfo ->
//                                userInfo.userService(customOAuth2UserService)
//                        )
//                        .defaultSuccessUrl("/", true)
//                        .failureUrl("/auth/login?error=true")
//                        .permitAll()
//                )
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/auth/login?logout")
                        .deleteCookies("JSESSIONID", "access_token")
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/error/403")
                )
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UsuarioServiceImpl usuario) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(usuario);
        authenticationProvider.setPasswordEncoder(this.passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

}