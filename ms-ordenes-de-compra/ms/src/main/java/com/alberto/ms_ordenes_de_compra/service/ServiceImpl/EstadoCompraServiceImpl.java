package com.albertolizana.ms_ordenes_de_compra.service.ServiceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.albertolizana.ms_ordenes_de_compra.dto.EstadoCompraResponseDto;
import com.albertolizana.ms_ordenes_de_compra.exception.ResourceNotFoundException;
import com.albertolizana.ms_ordenes_de_compra.repository.EstadoCompraRepository;
import com.albertolizana.ms_ordenes_de_compra.service.EstadoCompraService;

@Service
@Transactional
public class EstadoCompraServiceImpl implements EstadoCompraService {

    private final EstadoCompraRepository estadoCompraRepository;

    public EstadoCompraServiceImpl(EstadoCompraRepository estadoCompraRepository)
    {
        this.estadoCompraRepository = estadoCompraRepository;
    }

    @Override
    public List<EstadoCompraResponseDto> getTodosLosEstados() {
        return estadoCompraRepository.findAll()
                                .stream()
                                .map(ec -> EstadoCompraResponseDto
                                    .builder()
                                    .id(ec.getId())
                                    .estado(ec.getNombreEstado())
                                .build())
                            .toList();
    }

    @Override
    public EstadoCompraResponseDto getEstadoById(Long id) {
        return estadoCompraRepository.findById(id)
                                .map(ec -> EstadoCompraResponseDto
                                    .builder()
                                    .id(ec.getId())
                                    .estado(ec.getNombreEstado())
                                .build())
                            .orElseThrow(() -> new ResourceNotFoundException("No se encontro el Estado de la compra con id " + id));

    }

}
