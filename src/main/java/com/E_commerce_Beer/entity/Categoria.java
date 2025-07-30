package com.E_commerce_Beer.entity;

import com.E_commerce_Beer.Enum.ECategoria;
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
@Table(name = "categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoria_id")
    private Long categoriaId;

    @Enumerated(EnumType.STRING)
    private ECategoria nombre;
    private String descripcion;

    @OneToMany(mappedBy = "categoria", fetch = FetchType.EAGER)
    private Set<Producto> productos = new HashSet<>();
}
