package com.albertolizana.ms_ordenes_de_compra.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.albertolizana.ms_ordenes_de_compra.model.EstadoCompra;

@Repository
public interface EstadoCompraRepository extends JpaRepository<EstadoCompra, Long> {
    Optional<EstadoCompra> findByNombreEstado(String estado);
}
