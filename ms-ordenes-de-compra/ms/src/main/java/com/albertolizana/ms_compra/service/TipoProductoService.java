package com.albertolizana.ms_compra.service;

import java.util.List;

import com.albertolizana.ms_compra.dto.TipoProductoResponseDto;

public interface TipoProductoService {

    public List<TipoProductoResponseDto> getAllTipoProducto();
    public TipoProductoResponseDto getTipoProducto(Long id);

}
