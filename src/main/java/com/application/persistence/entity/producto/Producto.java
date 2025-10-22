package com.application.persistence.entity.producto;

import com.application.persistence.entity.categoria.Categoria;
import com.application.persistence.entity.categoria.SubCategoria;
import com.application.persistence.entity.compra.DetalleVenta;
import com.application.persistence.entity.producto.enums.ETipo;
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
@Table(name = "producto", uniqueConstraints = {
                @UniqueConstraint(columnNames = "codigo_producto", name = "uk_producto_codigo"),
                @UniqueConstraint(columnNames = { "nombre", "marca" }, name = "uk_producto_nombre_marca")
})
public class Producto {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "producto_id")
        private Long productoId;
        @Column(name = "codigo_producto")
        private String codigoProducto;
        private String imagen;
        private String nombre;
        private String marca;
        private String pais;
        @Column(name = "tipo")
        @Enumerated(EnumType.STRING)
        private ETipo eTipo;
        private double precio;
        private int stock;
        @Column(length = 700)
        private String descripcion;
        private boolean activo;

        // Cardinalidad con la tabla categoria
        @ManyToOne
        @JoinColumn(name = "categoria_id",
                referencedColumnName = "categoria_id",
                foreignKey = @ForeignKey(name = "fk_producto_categoria")
        )
        private Categoria categoria;

        // Cardinalidad con la tabla sub-categoria
        @ManyToOne
        @JoinColumn(name = "subcategoria_id",
                referencedColumnName = "subcategoria_id",
                foreignKey = @ForeignKey(name = "fk_producto_subcategoria")
        )
        private SubCategoria subCategoria;

        // Cardinalidad con la tabla detálle ventas
        @Builder.Default
        @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY)
        private Set<DetalleVenta> detalleVentas = new HashSet<>();

        // Agregar categoria a producto y viceversa (bidirectional)
        public void addCategoria(Categoria categoria) {
                this.setCategoria(categoria);
                categoria.getProductos().add(this);
        }

        // Eliminar categoria de producto y viceversa (bidirectional)
        public void deleteCategoria(Categoria categoria) {
                this.setCategoria(null);
                categoria.getProductos().remove(this);
        }

}