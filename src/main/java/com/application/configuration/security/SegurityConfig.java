package com.application.configuration.security;

import com.application.persistence.repository.RolRepository;
import com.application.persistence.repository.UsuarioRepository;
import com.application.service.implementation.usuario.UsuarioServicesImpl;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Data
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SegurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        try {
            return http
                    .csrf(AbstractHttpConfigurer::disable)
                    .sessionManagement(session -> session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                            .invalidSessionUrl("/auth/login")
                    )
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers(
                                    "/Assets/**",       // Recursos estáticos
                                    "/Js/**",
                                    "/Json/**",
                                    "/Css/**"
                            ).permitAll()
                            .requestMatchers(
                                    "/auth/**",
                                    "/usuario/**",
                                    "/error/**",
                                    "/error/").permitAll()
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
                            .loginPage("/auth/login")        // ← Página personalizada de login
                            .loginProcessingUrl("/auth/login") // ← Procesamiento del formulario
                            .defaultSuccessUrl("/", true)
                            .failureUrl("/auth/login?error=true")
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
    public UserDetailsService userDetailsService(
            UsuarioRepository usuarioRepository,
            RolRepository rolRepository, PasswordEncoder encoder) {
        return new UsuarioServicesImpl(usuarioRepository, rolRepository, encoder);
    }

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
    public SessionRegistry datosSession() {
        return new SessionRegistryImpl();
    }

}