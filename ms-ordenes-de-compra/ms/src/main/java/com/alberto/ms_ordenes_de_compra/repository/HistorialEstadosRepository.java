package com.albertolizana.ms_ordenes_de_compra.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.albertolizana.ms_ordenes_de_compra.model.HistorialEstados;

@Repository
public interface HistorialEstadosRepository extends JpaRepository<HistorialEstados, Long>{

    @Query
    ("""
        SELECT he FROM HistorialEstados he
        JOIN FETCH he.estadoCompra
        JOIN FETCH he.compra c
        JOIN FETCH c.cliente
    """)
    List<HistorialEstados> findAllWithRelations();

    @Query
    ("""
        SELECT he FROM HistorialEstados he
        JOIN FETCH he.estadoCompra
        JOIN FETCH he.compra c
        JOIN FETCH c.cliente
        WHERE he.idHistorialEstados = :id
    """)
    Optional<HistorialEstados> findWithRelations(@Param("id") Long id);

    List<HistorialEstados> findAllByFechaTerminoEstadoNull();
    
    Optional<HistorialEstados> findByFechaTerminoEstadoIsNullAndCompra_IdCompra(Long idCompra);

    List<HistorialEstados> findAllByCompra_idCompra(Long idCompra);

}

