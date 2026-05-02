package com.albertolizana.ms_compra.service;
import java.util.List;

import com.albertolizana.ms_compra.dto.DetalleCompraResponseDto;

public interface DetalleCompraService {

    public List<DetalleCompraResponseDto> getDetallesPorCompra(Long idCompra);
    public DetalleCompraResponseDto getDetalle(Long id);

}