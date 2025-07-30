package com.E_commerce_Beer.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "historia")
public class Historia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "historia_id")
    private Long historiaId;

    private String imagen;
    private String titulo;
    private String descripcion;
    @Column(name = "historia_completa")
    private String historiaCompleta;
    private LocalDate fecha;
    private Boolean activo;

    // Cardinalidad con la tabla comentario (relación bidireccional)
    @OneToMany(mappedBy = "historia", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Comentario> comentarios = new HashSet<>();
}
