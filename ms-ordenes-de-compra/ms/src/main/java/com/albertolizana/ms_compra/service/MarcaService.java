package com.albertolizana.ms_compra.service;

import java.util.List;

import com.albertolizana.ms_compra.dto.MarcaResponseDto;

public interface MarcaService {

    public List<MarcaResponseDto> getMarcas();
    public MarcaResponseDto getMarca(Long id);
    
}
