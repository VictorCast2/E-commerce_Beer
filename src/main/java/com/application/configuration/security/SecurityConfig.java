package com.application.configuration.security;

import com.application.configuration.Custom.CustomOAuth2UserService;
import com.application.persistence.repository.EmpresaRepository;
import com.application.persistence.repository.RolRepository;
import com.application.persistence.repository.UsuarioRepository;
import com.application.service.implementation.usuario.UsuarioServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configuración del filtro de seguridad para manejar la autenticación y autorización
     * Utiliza HttpSecurity para definir las reglas de seguridad de la aplicación
     */
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            CustomOAuth2UserService customOAuth2UserService
    ) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .invalidSessionUrl("/auth/login") // URL a la que redirigir si la sesión es inválida
                        .maximumSessions(2) // Número máximo de sesiones por usuario
                        .expiredUrl("/auth/login?expired") // Redirige si la sesión expiró
                        .sessionRegistry(sessionRegistry()) // Registra las sesiones activas
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/**",
                                "/usuario/**",
                                "/producto/**",
                                "/error/**",
                                "/auth/logout",
                                "/error/"
                        ).permitAll()
                        .requestMatchers("/admin/categoria/**").hasRole("ADMIN")
                        .requestMatchers("/admin/producto/**").hasRole("ADMIN")
                        .requestMatchers(
                                "/",
                                "/Assets/**",
                                "/Js/**",
                                "/Json/**",
                                "/Css/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .successHandler((request, response, authentication) -> {
                            response.sendRedirect("/auth/login?success=true");
                        })
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/auth/login?error=true")
                        .permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/auth/login")
                        .userInfoEndpoint(userInfo ->
                                userInfo.userService(customOAuth2UserService)
                        )
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/auth/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/auth/login?logout")
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID", "access_token")
                        .permitAll()
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/error/403")
                )
                .build();
    }

    /**
     * Configuración del UserDetailsService para manejar la carga de usuarios
     * Utiliza el UsuarioRepository y RolRepository para obtener los datos de usuario y rol
     */
    @Bean
    public UserDetailsService userDetailsService(
            UsuarioRepository usuarioRepository,
            RolRepository rolRepository, EmpresaRepository empresaRepository,
            PasswordEncoder encoder) {
        return new UsuarioServiceImpl(usuarioRepository, empresaRepository, rolRepository, encoder);
    }

    /**
     * Configuración del PasswordEncoder para manejar el cifrado de contraseñas
     * Utiliza BCryptPasswordEncoder para cifrar las contraseñas de los usuarios
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configuración del DaoAuthenticationProvider para manejar la autenticación de usuarios
     * Utiliza el UserDetailsService y PasswordEncoder definidos anteriormente
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    /**
     * Configuración del AuthenticationManager para manejar la autenticación de usuarios
     * Utiliza el UserDetailsService y PasswordEncoder definidos anteriormente
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity,
                                                       PasswordEncoder passwordEncoder,
                                                       UserDetailsService userDetailsService) throws Exception {
        AuthenticationManagerBuilder authBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        return authBuilder.build();
    }

    /**
     * Registro de sesiones para manejar múltiples sesiones de usuario
     * Permite rastrear las sesiones activas y gestionar el cierre de sesión
     */
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

}