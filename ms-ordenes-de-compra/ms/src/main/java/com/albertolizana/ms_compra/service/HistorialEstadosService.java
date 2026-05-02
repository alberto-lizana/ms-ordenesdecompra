package com.albertolizana.ms_compra.service;

import java.util.List;

import com.albertolizana.ms_compra.dto.CrearNuevoHistorialRequestDto;
import com.albertolizana.ms_compra.dto.HistorialEstadoResponseDto;

public interface HistorialEstadosService {

    public List<HistorialEstadoResponseDto> getTodosHistoriales();
    public HistorialEstadoResponseDto getHistorialById(Long id);
    public HistorialEstadoResponseDto crearEstadoHistorial(CrearNuevoHistorialRequestDto dto);
    public List<HistorialEstadoResponseDto> getTodosLosHistorialesActuales();
    public List<HistorialEstadoResponseDto> getHistoricoCompra(Long idCompra);

}
