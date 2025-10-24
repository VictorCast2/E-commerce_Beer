package com.application.service.interfaces;

import com.application.persistence.entity.usuario.Usuario;

import java.util.Map;

public interface EmailService {

    void sendEmail(String to, String subject, String plantilla, Map<String, Object> variables);

    void sendWelcomeEmail(Usuario usuario);
}
