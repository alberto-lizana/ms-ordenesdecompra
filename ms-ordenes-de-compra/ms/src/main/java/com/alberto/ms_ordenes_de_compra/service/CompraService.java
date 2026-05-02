package com.albertolizana.ms_ordenes_de_compra.service;

import java.util.List;

import com.albertolizana.ms_ordenes_de_compra.dto.CompraRequestDto;
import com.albertolizana.ms_ordenes_de_compra.dto.CompraResponseDto;

public interface CompraService {

    public List<CompraResponseDto> getCompras();
    public CompraResponseDto getCompra(Long id);
    public CompraResponseDto crearCompra(CompraRequestDto dto);

}
