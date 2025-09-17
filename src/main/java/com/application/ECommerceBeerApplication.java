package com.application;

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
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ECommerceBeerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ECommerceBeerApplication.class, args);
    }

}