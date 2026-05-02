package com.albertolizana.ms_ordenes_de_compra.service;

import java.util.List;

import com.albertolizana.ms_ordenes_de_compra.dto.MarcaResponseDto;

public interface MarcaService {

    public List<MarcaResponseDto> getMarcas();
    public MarcaResponseDto getMarca(Long id);
    
}
