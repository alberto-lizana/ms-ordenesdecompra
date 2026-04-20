package com.albertolizana.ms_ordenes_de_compra.service.ServiceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.albertolizana.ms_ordenes_de_compra.dto.TipoProductoResponseDto;
import com.albertolizana.ms_ordenes_de_compra.exception.ResourceNotFoundException;
import com.albertolizana.ms_ordenes_de_compra.repository.TipoProductoRepository;
import com.albertolizana.ms_ordenes_de_compra.service.TipoProductoService;

@Service
public class TipoProductoServiceImpl implements TipoProductoService {

    private final TipoProductoRepository tipoProductoRepository; 

    public TipoProductoServiceImpl(TipoProductoRepository tipoProductoRepository)
    {
        this.tipoProductoRepository = tipoProductoRepository;
    }

    @Override
    public List<TipoProductoResponseDto> getAllTipoProducto() {
        return tipoProductoRepository.findAll()
                                .stream()
                                .map(tp -> TipoProductoResponseDto
                                    .builder()
                                    .idTipoProducto(tp.getIdTipoProducto())
                                    .nombre(tp.getNombre())
                                .build())
                                .toList();
    }

    @Override
    public TipoProductoResponseDto getTipoProducto(Long id) {
        return tipoProductoRepository.findById(id)
                                .map(tp -> TipoProductoResponseDto
                                    .builder()
                                    .idTipoProducto(tp.getIdTipoProducto())
                                    .nombre(tp.getNombre())
                                .build()).orElseThrow(() -> new ResourceNotFoundException("no se ha encontrado el tipo de producto con id " + id));
    }

}
