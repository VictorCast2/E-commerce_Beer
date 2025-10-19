package com.application.persistence.entity.producto.enums;

public enum ETipo {
    UNIDAD, // Producto de una sola unidad
    CAJA, // Caja de productos iguales, ej: 24 cervezas
    PACK, // Pack de productos similares, ej: 6 cervezas variadas
    COMBO, // Mezcla de diferentes tipos, ej: 2 vinos y 3 cervezas
}