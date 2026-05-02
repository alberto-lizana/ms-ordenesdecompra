package com.albertolizana.ms_compra.service;

import java.util.List;

import com.albertolizana.ms_compra.dto.ProductoRequestDto;
import com.albertolizana.ms_compra.dto.ProductoResponseDto;

public interface ProductoService {

    public List<ProductoResponseDto> getProductos(); 
    public ProductoResponseDto getProducto(Long id); 
    public ProductoResponseDto modificarProducto(ProductoRequestDto dto);
}
