package com.application.persistence.entity.usuario;

import com.application.persistence.entity.compra.Compra;
import com.application.persistence.entity.empresa.Empresa;
import com.application.persistence.entity.rol.Rol;
import com.application.persistence.entity.usuario.enums.EIdentificacion;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import javax.validation.constraints.Pattern;
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
                @UniqueConstraint(columnNames = "cedula", name = "uk_usuario_cedula"),
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
    @Column(length = 15, nullable = false)
    private String cedula;
    @Column(length = 175, nullable = false)
    private String nombres;
    @Column(length = 175, nullable = false)
    private String apellidos;
    @Column(length = 20, nullable = false)
    private String telefono;
    @Column(nullable = false)
    private String imagen;
    @Column(length = 100, nullable = false)
    private String correo;
    @Column(length = 100, nullable = false)
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

    /**
     * Método para obtener los roles del usuario como una colección de GrantedAuthority.
     * Si el rol es nulo o su nombre es nulo, devuelve una lista vacía.
     * De lo contrario, devuelve una lista con el rol del usuario.
     *
     * @return Colección de GrantedAuthority
     */
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (rol == null || rol.getName() == null) {
            return Collections.emptyList();
        }
        return List.of(new SimpleGrantedAuthority("ROLE_" + rol.getName().name()));
    }

}