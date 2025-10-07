package com.application.configuration.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "E-commerce Beer API",
                description = "API para la gestión del sistema E-commerce Beer con autenticación mediante OAuth2 (Google).",
                version = "1.0.0",
                contact = @Contact(
                        name = "Victor Cast",
                        email = "victor@example.com"
                ),
                license = @License(
                        name = "Apache License, Version 2.0",
                        url = "https://www.apache.org/licenses/LICENSE"
                )
        ),
        servers = {
                @Server(
                        description = "Servidor de desarrollo",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Servidor de producción",
                        url = "http://localhost:8080"
                )
        },
        security = {
                @SecurityRequirement(name = "OAuth2 Google"),
                @SecurityRequirement(name = "Bearer Token") // 👈 Se añaden ambos métodos globalmente
        } // 🔒 Se aplica a todos los endpoints por defecto
)

// 🔐 Esquema 1: OAuth2 (Google)
@SecurityScheme(
        name = "OAuth2 Google",
        description = "Autenticación con Google OAuth2",
        type = SecuritySchemeType.OAUTH2,
        in = SecuritySchemeIn.HEADER,
        flows = @OAuthFlows(
                authorizationCode = @OAuthFlow(
                        authorizationUrl = "https://accounts.google.com/o/oauth2/v2/auth",
                        tokenUrl = "https://oauth2.googleapis.com/token",
                        scopes = {
                                @OAuthScope(name = "openid", description = "Acceso OpenID"),
                                @OAuthScope(name = "profile", description = "Acceso al perfil del usuario"),
                                @OAuthScope(name = "email", description = "Acceso al correo del usuario")
                        }
                )
        )
)

// 🔑 Esquema 2: HTTP Basic
@SecurityScheme(
        name = "Basic Auth",
        description = "Autenticación mediante credenciales HTTP Basic (usuario y contraseña)",
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)

public class SwaggerConfig {
}