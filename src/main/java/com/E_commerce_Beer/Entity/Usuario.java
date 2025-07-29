package com.E_commerce_Beer.Entity;

import com.E_commerce_Beer.Enum.EmunTipoDocumento;
import com.E_commerce_Beer.Enum.EnumRol;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Usuario")
public class Usuario{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 45)
    private String Nombre;
    @Column(nullable = false, length = 45)
    private String Apellido;
    @Column(nullable = false, length = 80, unique = true)
    private String Correo;
    @Column(nullable = false, length = 45)
    private String Contrasenna;
    @Column(nullable = false, length = 10)
    private int Telefono;
    @Column(nullable = false, unique = true)
    private EmunTipoDocumento TipoDocumento;
    @Column(nullable = false, unique = true)
    private Long Cedula;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "Roles", joinColumns = @JoinColumn(name = "usuario_id"))
    @Column(name = "rol", nullable = false)
    private Set<EnumRol> Roles = new HashSet<>();

}