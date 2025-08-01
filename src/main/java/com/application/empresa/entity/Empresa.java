package com.application.empresa.entity;

import com.application.empresa.enums.ESector;
import jakarta.persistence.*;
import lombok.*;

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

    private String nit;
    @Column(name = "razon_social")
    private String razonSocial;
    private String ciudad;
    private String direccion;
    private String telefono;
    private String correo;
    @Column(name = "sector")
    @Enumerated(EnumType.STRING)
    private ESector eSector;
    private boolean activo;
}
