package com.albertolizana.ms_ordenes_de_compra.service;
import java.util.List;

import com.albertolizana.ms_ordenes_de_compra.dto.DetalleCompraResponseDto;

public interface DetalleCompraService {

    public List<DetalleCompraResponseDto> getDetallesPorCompra(Long idCompra);
    public DetalleCompraResponseDto getDetalle(Long id);

}