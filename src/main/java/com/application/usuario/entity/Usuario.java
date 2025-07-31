package com.application.usuario.entity;

import com.application.compra.entity.Compra;
import com.application.empresa.entity.Empresa;
import com.application.rol.entity.Rol;
import jakarta.persistence.*;
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
    private String cedula;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String correo;
    private String password;

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
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private Set<Compra> compras = new HashSet<>();
}
