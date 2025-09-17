package com.application.utils;

import com.application.persistence.entity.empresa.Empresa;
import com.application.persistence.entity.empresa.enums.ESector;
import com.application.persistence.entity.rol.Rol;
import com.application.persistence.entity.rol.enums.ERol;
import com.application.persistence.entity.usuario.Usuario;
import com.application.persistence.entity.usuario.enums.EIdentificacion;
import com.application.persistence.repository.EmpresaRepository;
import com.application.persistence.repository.RolRepository;
import com.application.persistence.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    @Bean
    @Profile("dev")
    public CommandLineRunner init(UsuarioRepository usuarioRepository, RolRepository rolRepository, EmpresaRepository empresaRepository) {
        return args -> {

            /* Cargar roles a la base de datos a partir del ERol */
            for (ERol rolEnum : ERol.values()) {
                rolRepository.findByName(rolEnum)
                        .orElseGet(() -> {
                            Rol rol = Rol.builder()
                                    .name(rolEnum)
                                    .build();
                            return rolRepository.save(rol);
                        });
            }

            /* Crear Usuarios */
            // Admin
            Usuario userJose = Usuario.builder()
                    .tipoIdentificacion(EIdentificacion.CC)
                    .numeroIdentificacion("123456789")
                    .nombres("Jose Andres")
                    .apellidos("Torres Diaz")
                    .telefono("310 2233445")
                    .correo("jose@mail.com")
                    .password("$2a$10$rj3PmRqB76o2VrobVRdCf.s2Q4S3HDnvVHeAmi8Uxdp.GWrLoqiMq")
                    .rol(Rol.builder().name(ERol.ADMIN).build())
                    .build();

            // Persona de Contacto
            Empresa empresaTheresa = Empresa.builder()
                    .nit("987654321-0")
                    .razonSocial("Aurum Tech")
                    .ciudad("Cartagena")
                    .direccion("Barrio Mz## Lt## Et#")
                    .telefono("320 4569875")
                    .correo("aurum@tech.com")
                    .eSector(ESector.BAR)
                    .activo(true)
                    .build();

            Usuario userTheresa = Usuario.builder()
                    .tipoIdentificacion(EIdentificacion.CC)
                    .numeroIdentificacion("987654321")
                    .nombres("Theresa Andrea")
                    .apellidos("Torres Diaz")
                    .telefono("320 2233445")
                    .correo("theresa@mail.com")
                    .password("$2a$10$rj3PmRqB76o2VrobVRdCf.s2Q4S3HDnvVHeAmi8Uxdp.GWrLoqiMq")
                    .empresa(empresaTheresa)
                    .rol(Rol.builder().name(ERol.PERSONA_CONTACTO).build())
                    .build();

            // Invitado
            Usuario userElysia = Usuario.builder()
                    .tipoIdentificacion(EIdentificacion.CC)
                    .numeroIdentificacion("1234567890")
                    .nombres("Elysia Andrea")
                    .apellidos("Torres Diaz")
                    .telefono("330 2233445")
                    .correo("elysia@mail.com")
                    .password("$2a$10$rj3PmRqB76o2VrobVRdCf.s2Q4S3HDnvVHeAmi8Uxdp.GWrLoqiMq")
                    .rol(Rol.builder().name(ERol.INVITADO).build())
                    .build();

            if (!usuarioRepository.existsByCorreo(userJose.getCorreo())) {
                usuarioRepository.save(userJose);
            }

            if (!usuarioRepository.existsByCorreo(userTheresa.getCorreo())) {
                empresaRepository.save(empresaTheresa);
                usuarioRepository.save(userTheresa);
            }

            if (!usuarioRepository.existsByCorreo(userElysia.getCorreo())) {
                usuarioRepository.save(userElysia);
            }

        };
    }
}
