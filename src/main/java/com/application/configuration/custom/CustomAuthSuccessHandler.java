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
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
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

        SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);

        String redirectAfter = (savedRequest != null)
                ? savedRequest.getRedirectUrl()
                : "/";

        String rol = authentication.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("PERSONA_NATURAL");

        String nextEncoded = Base64.getUrlEncoder().encodeToString(redirectAfter.getBytes(StandardCharsets.UTF_8));
        String rolEncoded = Base64.getEncoder().encodeToString(rol.getBytes(StandardCharsets.UTF_8));

        String url = String.format("/auth/login?success=true&rol=%s&next=%s", rolEncoded, nextEncoded);

        this.getRedirectStrategy().sendRedirect(request, response, url);

        super.onAuthenticationSuccess(request, response, authentication);
    }

}