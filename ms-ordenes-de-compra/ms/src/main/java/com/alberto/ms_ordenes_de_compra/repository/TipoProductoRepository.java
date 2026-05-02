package com.albertolizana.ms_ordenes_de_compra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.albertolizana.ms_ordenes_de_compra.model.TipoProducto;

@Repository
public interface TipoProductoRepository extends JpaRepository<TipoProducto, Long>{

}
