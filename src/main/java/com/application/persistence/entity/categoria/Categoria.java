package com.application.persistence.entity.categoria;

import com.application.persistence.entity.producto.Producto;
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
@Table(name = "categoria", uniqueConstraints = {
                @UniqueConstraint(columnNames = "nombre", name = "uk_categoria_nombre")
})
public class Categoria {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "categoria_id", nullable = false)
        private Long categoriaId;
        @Column(length = 75, nullable = false)
        private String nombre;
        @Column(length = 300, nullable = false)
        private String descripcion;
        @Column(nullable = false)
        private boolean activo;

        // Cardinalidad con la tabla producto
        @Builder.Default
        @ManyToMany(mappedBy = "categorias")
        private Set<Producto> productos = new HashSet<>();
}