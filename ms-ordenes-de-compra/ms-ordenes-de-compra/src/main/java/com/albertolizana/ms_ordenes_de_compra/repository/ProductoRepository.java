package com.albertolizana.ms_ordenes_de_compra.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.albertolizana.ms_ordenes_de_compra.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>{

    @Query
    ("""
        SELECT p FROM Producto p
        JOIN FETCH p.tipoProducto
        JOIN FETCH p.marca
    """)
    List<Producto> findAllWithRelations();

    @Query
    ("""
        SELECT p FROM Producto p
        JOIN FETCH p.tipoProducto
        JOIN FETCH p.marca
        WHERE p.idProducto = :id
    """)
    Optional<Producto> findWithRelations(@Param("id") Long id);
}
