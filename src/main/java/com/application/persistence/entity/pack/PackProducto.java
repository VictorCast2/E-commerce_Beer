package com.application.persistence.entity.pack;

import com.application.persistence.entity.producto.Producto;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "pack_producto")
public class PackProducto {

    @EmbeddedId
    private PackProductoId packProductoId;

    private int cantidad;

    // Cardinalidad con la tabla pack
    @ManyToOne
    @MapsId("packId")
    @JoinColumn(
            name = "pack_id",
            referencedColumnName = "pack_id",
            foreignKey = @ForeignKey(name = "fk_packProducto_pack")
    )
    private Pack pack;

    // Cardinalidad con la tabla producto
    @ManyToOne
    @MapsId("productoId")
    @JoinColumn(
            name = "producto_id",
            referencedColumnName = "producto_id",
            foreignKey = @ForeignKey(name = "fk_packProducto_producto")
    )
    private Producto producto;
}
