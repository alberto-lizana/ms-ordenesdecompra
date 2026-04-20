package com.albertolizana.ms_ordenes_de_compra.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.albertolizana.ms_ordenes_de_compra.model.DetalleCompra;

@Repository
public interface DetalleCompraRepository extends JpaRepository<DetalleCompra, Long>{

    @Query
    ("""
        SELECT dc FROM DetalleCompra dc
        JOIN FETCH dc.producto p
        JOIN FETCH p.tipoProducto
        JOIN FETCH p.marca
        WHERE dc.compra.idCompra = :idCompra
    """)
    List<DetalleCompra> findByCompraWithRelations(@Param("idCompra") Long idCompra);

    @Query
    ("""
        SELECT dc FROM DetalleCompra dc
        JOIN FETCH dc.producto p
        JOIN FETCH p.tipoProducto
        JOIN FETCH p.marca
        WHERE dc.idDetalleCompra = :id
    """)
    Optional<DetalleCompra> findByIdWithRelations(@Param("id") Long id);

}

