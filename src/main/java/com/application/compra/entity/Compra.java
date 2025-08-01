package com.application.compra.entity;

import com.application.compra.enums.EEstado;
import com.application.compra.enums.EMetodoPago;
import com.application.usuario.entity.Usuario;
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
    @Column(name = "compra_id")
    private Long compraId;
    @Column(name = "metodo_pago")
    @Enumerated(EnumType.STRING)
    private EMetodoPago eMetodoPago;
    private double subtotal;
    private String cuponDescuento;
    private double iva;
    private double total;
    private LocalDateTime fecha;
    @Column(name = "estado")
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
