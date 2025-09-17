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

    @Column(name = "nit")
    @Pattern(regexp = "", message = "El nit de la empresa debe ser válido")
    private String nit;

    @Column(name = "razon_social")
    private String razonSocial;

    @Column(name = "ciudad")
    private String ciudad;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "telefono")
    @Pattern(regexp = "^(\\+\\d{1,3}[- ])?\\d{10}$", message = "El número de teléfono debe tener 10 dígitos y puede incluir un código de país opcional.")
    private String telefono;

    @Column(name = "correo", length = 100)
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "El correo debe ser válido")
    @Email(message = "El correo debe ser válido")
    private String correo;

    @Column(name = "sector")
    @Enumerated(EnumType.STRING)
    private ESector eSector;

    @Column(name = "activo")
    private boolean activo;

}