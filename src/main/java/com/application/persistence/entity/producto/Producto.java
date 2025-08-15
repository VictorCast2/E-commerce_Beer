package com.application.persistence.entity.producto;

import com.application.persistence.entity.pack.PackProducto;
import com.application.persistence.shared.ItemProducto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(
        name = "producto",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"nombre", "marca"}, name = "uk_producto_nombre_marca"),
                @UniqueConstraint(columnNames = "descripcion", name = "uk_producto_descripcion")
        }
)
public class Producto extends ItemProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "producto_id")
    private Long productoId;
    private String marca;
    private String presentacion;

    // Cardinalidad con la tabla pack_producto
    @Builder.Default
    @OneToMany(mappedBy = "producto",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private Set<PackProducto> packProductos = new HashSet<>();

}