package com.application.configuration.custom;

import com.application.persistence.entity.usuario.Usuario;
import com.application.persistence.repository.UsuarioRepository;
import com.application.service.interfaces.EmailService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Collection;

@Component
@RequiredArgsConstructor
public class CustomAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UsuarioRepository usuarioRepository;
    private final EmailService emailService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();
        String correo = principal.getCorreo();

        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElse(null);

        if (usuario != null && (usuario.getPassword() == null)) {
            this.getRedirectStrategy().sendRedirect(request, response, "/auth/completar-registro");
            return;
        }

        if (usuario != null) {
            this.emailService.sendEmailLoginSuccessful(usuario, request);
        }

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            String rol = authority.getAuthority();

            if (rol.equals("ROLE_ADMIN")) {
                this.getRedirectStrategy().sendRedirect(request, response, "/admin/principal/");
                return;
            }
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }

}