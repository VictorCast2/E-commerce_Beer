package com.E_commerce_Beer.entity;

import com.E_commerce_Beer.Enum.EEstado;
import com.E_commerce_Beer.Enum.EMetodoPago;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "compra")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long compraId;
    @Enumerated(EnumType.STRING)
    private EMetodoPago eMetodoPago;
    private double subtotal;
    private String cuponDescuento;
    private double iva;
    private double total;
    private LocalDateTime fecha;
    @Enumerated(EnumType.STRING)
    private EEstado eEstado;

    // Cardinalidad con la tabla usuario (relación bidireccional)
    @ManyToOne
    @JoinColumn(
            name = "usuario_id",
            referencedColumnName = "usuario_id",
            foreignKey = @ForeignKey(name = "fk_compra_usuario")
    )
    private Usuario usuario;
}
