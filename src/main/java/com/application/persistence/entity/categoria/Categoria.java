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
        private String imagen;
        @Column(length = 175, nullable = false)
        private String nombre;
        @Column(length = 500, nullable = false)
        private String descripcion;
        @Column(nullable = false)
        private boolean activo;

        // Cardinalidad con la tabla producto
        @Builder.Default
        @OneToMany(mappedBy = "categoria", fetch = FetchType.LAZY)
        private Set<Producto> productos = new HashSet<>();

        // Cardinalidad con la tabla categorías
        @Builder.Default
        @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
        private Set<SubCategoria> subCategorias = new HashSet<>();

        // Agregar categoria a sub-categoria y viceversa (bidirectional)
        public void addSubCategoria(SubCategoria subCategoria) {
                subCategoria.setCategoria(this);
                this.subCategorias.add(subCategoria);
        }

        // Eliminar categoria de sub-categoria y viceversa (bidirectional)
        public void deleteSubCategoria(SubCategoria subCategoria) {
                subCategoria.setCategoria(null);
                this.subCategorias.remove(subCategoria);
        }
}