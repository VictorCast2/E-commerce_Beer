package com.application.persistence.entity.empresa;

import com.application.persistence.entity.empresa.enums.ESector;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(
        name = "empresa",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = "nit", name = "uk_empresa_nit"),
            @UniqueConstraint(columnNames = "razon_social", name = "uk_empresa_razon_social"),
            @UniqueConstraint(columnNames = "telefono", name = "uk_empresa_telefono"),
            @UniqueConstraint(columnNames = "correo", name = "uk_empresa_correo")
        }
)
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "empresa_id")
    private Long empresaId;

    @Column(length = 10, nullable = false)
    private String nit;
    @Column(length = 175, name = "razon_social", nullable = false)
    private String razonSocial;
    @Column(nullable = false)
    private String ciudad;
    @Column(nullable = false)
    private String direccion;
    @Column(length = 20, nullable = false)
    private String telefono;
    @Column(length = 100, nullable = false)
    private String correo;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ESector eSector;
    @Column(nullable = false)
    private boolean activo;

}