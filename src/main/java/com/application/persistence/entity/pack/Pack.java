package com.application.persistence.entity.pack;

import com.application.persistence.entity.categoria.Categoria;
import com.application.persistence.entity.pack.enums.ETipo;
import com.application.persistence.entity.compra.DetalleVenta;
import com.application.persistence.shared.ItemProducto;
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
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "pack_categoria",
            joinColumns = @JoinColumn(name = "pack_id", referencedColumnName = "pack_id",
                    foreignKey = @ForeignKey(name = "fk_pack_categoria")),
            inverseJoinColumns = @JoinColumn(name = "categoria_id", referencedColumnName = "categoria_id",
                    foreignKey = @ForeignKey(name = "fk_categoria_pack"))
    )
    private Set<Categoria> categorias = new HashSet<>();

    // Cardinalidad con la tabla pack_producto
    @OneToMany(mappedBy = "pack", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PackProducto> packProductos = new HashSet<>();

    // Cardinalidad con la tabla detálle ventas
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
}
