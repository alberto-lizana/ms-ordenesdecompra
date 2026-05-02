package com.albertolizana.ms_compra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.albertolizana.ms_compra.model.Marca;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long>{

}
