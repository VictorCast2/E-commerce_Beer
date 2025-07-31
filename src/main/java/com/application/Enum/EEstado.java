package com.application.Enum;

public enum EEstado {
    PENDIENTE,         // Pedido generado pero no pagado
    PAGADO,            // Compra confirmada
    EN_PROCESO,        // Preparando el pedido
    ENVIADO,           // En camino
    ENTREGADO,         // Confirmada la entrega
    CANCELADO,         // Cancelación por usuario o tienda
    REEMBOLSADO        // Devolución con reembolso aplicado
}
