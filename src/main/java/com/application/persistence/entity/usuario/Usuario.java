package com.application.persistence.entity.usuario;

import com.application.persistence.entity.compra.Compra;
import com.application.persistence.entity.empresa.Empresa;
import com.application.persistence.entity.rol.Rol;
import com.application.persistence.entity.usuario.enums.EIdentificacion;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(
        name = "usuario",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "numeroIdentificacion", name = "uk_usuario_numero_identificacion"),
                @UniqueConstraint(columnNames = "telefono", name = "uk_usuario_telefono"),
                @UniqueConstraint(columnNames = "correo", name = "uk_usuario_correo")
        }
)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;
    @Column(name = "tipo_identificacion")
    @Enumerated(EnumType.STRING)
    private EIdentificacion tipoIdentificacion;
    @Column(name = "numero_identificacion", length = 15)
    private String numeroIdentificacion;
    private String imagen;
    @Column(length = 175)
    private String nombres;
    @Column(length = 175)
    private String apellidos;
    @Column(length = 20)
    private String telefono;
    @Column(length = 100, nullable = false)
    private String correo;
    @Column(length = 100)
    private String password;

    @Column(name = "is_enabled")
    @Builder.Default
    private boolean isEnabled = true;

    @Column(name = "account_non_expired")
    @Builder.Default
    private boolean accountNonExpired = true;

    @Column(name = "account_non_locked")
    @Builder.Default
    private boolean accountNonLocked = true;

    @Column(name = "credentials_non_expired")
    @Builder.Default
    private boolean credentialsNonExpired = true;

    // Cardinalidad con la tabla rol (relación unidireccional)
    @ManyToOne
    @JoinColumn(
            name = "rol_id",
            referencedColumnName = "rol_id",
            foreignKey = @ForeignKey(name = "fk_usuario_rol")
    )
    private Rol rol;

    // Cardinalidad con la tabla empresas (relación unidireccional)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "empresa_id",
            referencedColumnName = "empresa_id",
            foreignKey = @ForeignKey(name = "fk_usuario_empresa")
    )
    private Empresa empresa;

    // Cardinalidad con la tabla compra (relación bidireccional)
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private Set<Compra> compras = new HashSet<>();

}