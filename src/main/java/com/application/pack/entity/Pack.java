package com.application.pack.entity;

import com.application.pack.enums.ETipo;
import com.application.compra.entity.DetalleVenta;
import com.application.shared.ItemProducto;
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

    // Cardinalidad con la tabla pack_producto
    @OneToMany(mappedBy = "pack", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<com.application.pack.entity.PackProducto> packProductos = new HashSet<>();

    // Cardinalidad con la tabla detálle ventas
    @OneToMany(mappedBy = "pack", fetch = FetchType.LAZY)
    private Set<DetalleVenta> detalleVentas = new HashSet<>();
}
