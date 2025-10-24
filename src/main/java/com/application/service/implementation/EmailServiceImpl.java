package com.application.service.implementation;

import com.application.persistence.entity.usuario.Usuario;
import com.application.service.interfaces.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

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
}
