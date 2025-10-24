package com.application.service.implementation;

import com.application.persistence.entity.usuario.Usuario;
import com.application.service.interfaces.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    /**
     *
     * @param to
     * @param subject
     * @param plantilla
     * @param variables
     */
    @Override
    public void sendEmail(String to, String subject, String plantilla, Map<String, Object> variables) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            Context context = new Context();
            context.setVariables(variables);

            String contenidoHtml = templateEngine.process("email/" + plantilla, context);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(contenidoHtml, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("ERROR: no se puedo enviar el email: " + e.getMessage(), e);
        }
    }

    /**
     * @param usuario
     */
    @Override
    public void sendWelcomeEmail(Usuario usuario) {
        try {
            Map<String, Object> variables = new HashMap<>();
            variables.put("nombre", usuario.getNombres() + " " + usuario.getApellidos());
            variables.put("tipoUsuario", usuario.getRol().getName().getDescripcion());
            variables.put("urlLogin", "http://localhost:8080/auth/login");

            if (usuario.getEmpresa() != null) {
                variables.put("empresa", usuario.getEmpresa().getRazonSocial());
            }

            this.sendEmail(usuario.getCorreo(), "¡Bienvenido a Nuestro Sistema!", "email-bienvenida", variables);

        } catch (Exception e) {
            throw new RuntimeException("Error al enviar el email de bienvenida" + e.getMessage(), e);
        }
    }

    /**
     * @param usuario
     * @param request
     */
    @Override
    public void sendEmailLoginSuccessful(Usuario usuario, HttpServletRequest request) {
        try {
            Map<String, Object> variables = new HashMap<>();
            variables.put("nombre", usuario.getNombres() + " " + usuario.getApellidos());
            variables.put("fechaLogin", LocalDateTime.now());
            variables.put("dispositivo", this.getDeviceInfo(request));

            this.sendEmail(usuario.getCorreo(), "Inicio de Sesión Exitoso - Seguridad", "email-login-exitoso", variables);
        } catch (Exception e) {
            throw new RuntimeException("Error al enviar el email de inicio de sesión exitoso" + e.getMessage(), e);
        }
    }

    /**
     * @param request
     * @return
     */
    @Override
    public String getDeviceInfo(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        if (userAgent != null) {
            if (userAgent.contains("Mobile")) {
                return "Dispositivo Móvil";
            } else if (userAgent.contains("Windows")) {
                return "Computadora Windows";
            } else if (userAgent.contains("Mac")) {
                return "Computadora Mac";
            } else if (userAgent.contains("Linux")) {
                return "Computadora Linux";
            }
        }
        return "Dispositivo Desconocido";
    }
}
