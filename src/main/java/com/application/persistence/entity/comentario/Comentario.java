package com.application.persistence.entity.comentario;

import com.application.persistence.entity.historia.Historia;
import com.application.persistence.entity.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "comentario")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comentario_id")
    private Long comentarioId;
    private String titulo;
    private String mensaje;
    private int calificacion;
    private LocalDate fecha;

    // Cardinalidad con la tabla usuario (relación unidireccional)
    @ManyToOne
    @JoinColumn(
            name = "usuario_id",
            referencedColumnName = "usuario_id",
            foreignKey = @ForeignKey(name = "fk_comentario_usuario")
    )
    private Usuario usuario;

    // Cardinalidad con la tabla historia (relación bidireccional)
    @ManyToOne
    @JoinColumn(
            name = "historia_id",
            referencedColumnName = "historia_id",
            foreignKey = @ForeignKey(name = "fk_comentario_historia")
    )
    private Historia historia;
}
