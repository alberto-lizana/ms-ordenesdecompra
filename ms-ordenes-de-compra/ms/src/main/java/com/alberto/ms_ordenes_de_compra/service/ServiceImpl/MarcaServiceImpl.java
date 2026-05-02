package com.albertolizana.ms_ordenes_de_compra.service.ServiceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.albertolizana.ms_ordenes_de_compra.dto.MarcaResponseDto;
import com.albertolizana.ms_ordenes_de_compra.exception.ResourceNotFoundException;
import com.albertolizana.ms_ordenes_de_compra.repository.MarcaRepository;
import com.albertolizana.ms_ordenes_de_compra.service.MarcaService;

@Service
public class MarcaServiceImpl implements MarcaService {

    private final MarcaRepository marcaRepository;

    public MarcaServiceImpl(MarcaRepository marcaRepository)
    {
        this.marcaRepository = marcaRepository;
    }

    @Override
    public List<MarcaResponseDto> getMarcas() {
        return marcaRepository.findAll()
                        .stream()
                        .map(m -> MarcaResponseDto
                            .builder()
                            .idMarca(m.getIdMarca())
                            .nombre(m.getNombre())
                        .build())
                        .toList();
    }

    @Override
    public MarcaResponseDto getMarca(Long id) {
        return marcaRepository.findById(id)
                            .map(m -> MarcaResponseDto
                                .builder()
                                .idMarca(m.getIdMarca())
                                .nombre(m.getNombre())
                            .build())
                            .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado la marca con id " + id));
    }

}
