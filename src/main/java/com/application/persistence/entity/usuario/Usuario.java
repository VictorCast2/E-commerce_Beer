package com.application.persistence.entity.usuario;

import com.application.persistence.entity.compra.Compra;
import com.application.persistence.entity.empresa.Empresa;
import com.application.persistence.entity.rol.Rol;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(
        name = "usuario",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "cedula", name = "uk_usuario_cedula"),
                @UniqueConstraint(columnNames = "telefono", name = "uk_usuario_telefono"),
                @UniqueConstraint(columnNames = "correo", name = "uk_usuario_correo")
        }
)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "cedula", nullable = false)
    private String cedula;
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombres;
    @Column(name = "apellido", nullable = false, length = 50)
    private String apellidos;
    @Size(min = 10, max = 10, message = "El telefono debe tener un minimo de 10 y maximo de 10 caracteres")
    @Column(name = "telefono", nullable = false)
    private int telefono;
    @Column(name = "imagen", nullable = false)
    private String imagen;
    @Email(message = "El correo debe ser válido")
    @Column(name = "correo", nullable = false, length = 100)
    private String correo;
    @Column(name = "contrasenna", nullable = false, length = 100)
    private String contrasenna;

    // Cardinalidad con la tabla rol (relación unidireccional)
    @ManyToOne
    @JoinColumn(
            name = "rol_id",
            referencedColumnName = "rol_id",
            foreignKey = @ForeignKey(name = "fk_usuario_rol")
    )
    private Rol rol;

    // Cardinalidad con la tabla empresas (relación unidireccional)
    @ManyToOne
    @JoinColumn(
            name = "empresa_id",
            referencedColumnName = "empresa_id",
            foreignKey = @ForeignKey(name = "fk_usuario_empresa")
    )
    private Empresa empresa;

    // Cardinalidad con la tabla compra (relación bidireccional)
    @Column(name = "compras")
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private Set<Compra> compras = new HashSet<>();

    @Column(name = "is_enabled")
    private boolean isEnabled;

    @Column(name = "account_No_Expired")
    private boolean accountNoExpired;

    @Column(name = "account_No_Locked")
    private boolean accountNoLocked;

    @Column(name = "credential_No_Expired")
    private boolean credentialNoExpired;

}