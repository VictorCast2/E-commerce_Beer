package com.application.persistence.entity.pack;

import com.application.persistence.entity.categoria.Categoria;
import com.application.persistence.entity.pack.enums.ETipo;
import com.application.persistence.entity.compra.DetalleVenta;
import com.application.persistence.entity.producto.Producto;
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
        name = "pack",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"nombre", "tipo"}, name = "uk_pack_nombre_tipo")
        }
)
public class Pack extends ItemProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pack_id")
    private Long packId;

    @Column(name = "tipo")
    @Enumerated(EnumType.STRING)
    private ETipo eTipo;

    // Cardinalidad con la tabla categoria
    @Builder.Default
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "pack_categoria",
            joinColumns = @JoinColumn(name = "pack_id", referencedColumnName = "pack_id",
                    foreignKey = @ForeignKey(name = "fk_pack_categoria")),
            inverseJoinColumns = @JoinColumn(name = "categoria_id", referencedColumnName = "categoria_id",
                    foreignKey = @ForeignKey(name = "fk_categoria_pack"))
    )
    private Set<Categoria> categorias = new HashSet<>();

    // Cardinalidad con la tabla pack_producto
    @Builder.Default
    @OneToMany(mappedBy = "pack", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PackProducto> packProductos = new HashSet<>();

    // Cardinalidad con la tabla detálle ventas
    @Builder.Default
    @OneToMany(mappedBy = "pack", fetch = FetchType.LAZY)
    private Set<DetalleVenta> detalleVentas = new HashSet<>();

    // Agregar categoria a pack y viceversa (bidirectional)
    public void addCategoria(Categoria categoria) {
        categorias.add(categoria);
        categoria.getPacks().add(this);
    }

    // Eliminar categoria de pack y viceversa (bidirectional)
    public void deleteCategoria(Categoria categoria) {
        categorias.remove(categoria);
        categoria.getPacks().remove(this);
    }

    // Agregar producto a pack y viceversa (bidireccional)
    public void addProducto(Producto producto, int cantidad) {
        PackProducto packProducto = PackProducto.builder()
                .pack(this)
                .producto(producto)
                .cantidad(cantidad)
                .build();
        packProductos.add(packProducto);
        producto.getPackProductos().add(packProducto);
    }

    // Eliminar producto de pack y viceversa (bidireccional)
    public void deleteProducto(Producto producto) {
        packProductos.removeIf(pp -> pp.getProducto().equals(producto));
        producto.getPackProductos().removeIf(pp -> pp.getPack().equals(this));
    }

    /**
     * Nota: aunque el dueño real de la relación en JPA para los métodos addProducto() y deleteProducto()
     * es PackProducto, estos métodos se definen aquí porque en el dominio del negocio, es Pack quien
     * agrega o quita productos
     */
}
