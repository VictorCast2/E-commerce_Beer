package com.application.persistence.entity.pack;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PackProductoId implements Serializable {

    @Column(name = "pack_id")
    private Long packId;
    @Column(name = "producto_id")
    private Long productoId;
}
