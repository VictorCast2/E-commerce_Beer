package com.application.persistence.entity.categoria;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "sub_categorias")
public class SubCategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "categoria_id",
            referencedColumnName = "categoria_id",
            foreignKey = @ForeignKey(name = "fk_sub-Categoria_categoria")
    )
    private Categoria categoria;
}
