package com.application.persistence.entity.compra;

import com.application.persistence.entity.compra.enums.EEstado;
import com.application.persistence.entity.compra.enums.EMetodoPago;
import com.application.persistence.entity.usuario.Usuario;
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

    // Cardinalidad con la tabla usuario
    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "usuario_id", foreignKey = @ForeignKey(name = "fk_compra_usuario"))
    private Usuario usuario;
}