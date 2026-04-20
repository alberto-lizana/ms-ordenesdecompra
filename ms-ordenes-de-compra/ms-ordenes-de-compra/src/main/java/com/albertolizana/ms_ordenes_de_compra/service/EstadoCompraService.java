package com.albertolizana.ms_ordenes_de_compra.service;

import java.util.List;

import com.albertolizana.ms_ordenes_de_compra.dto.EstadoCompraResponseDto;

public interface EstadoCompraService {

    public List<EstadoCompraResponseDto> getTodosLosEstados();
    public EstadoCompraResponseDto getEstadoById(Long id);
    
}
