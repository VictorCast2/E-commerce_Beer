package com.application;

import com.application.rol.entity.Rol;
import com.application.rol.enums.ERol;
import com.application.rol.repository.RolRepository;
import com.application.usuario.entity.Usuario;
import com.application.usuario.repository.UsuarioRepository;
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