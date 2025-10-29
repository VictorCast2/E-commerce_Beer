package com.application.persistence.repository;

import com.application.persistence.entity.producto.Producto;
import com.application.persistence.entity.producto.enums.ETipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByActivoTrue();

    List<Producto> findByCategoria_CategoriaId(Long categoriaId);

    List<Producto> findByeTipoNotAndActivoTrue(ETipo eTipo);

    @Query("""
            SELECT p
              FROM Producto p
              JOIN p.detalleVentas dv
             WHERE p.activo = true
             GROUP BY p
             ORDER BY SUM(dv.cantidad) DESC
            """)
    List<Producto> findProductosMasVendidosActivos();
}