package com.albertolizana.ms_ordenes_de_compra.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.albertolizana.ms_ordenes_de_compra.dto.EstadoCompraResponseDto;
import com.albertolizana.ms_ordenes_de_compra.service.EstadoCompraService;

@RestController
@RequestMapping("/estado-compra")
public class EstadoCompraController {

    private final EstadoCompraService estadoCompraService;
    
    public EstadoCompraController(EstadoCompraService estadoCompraService)
    {
        this.estadoCompraService = estadoCompraService;
    }

    @GetMapping("/all")
    public ResponseEntity <List<EstadoCompraResponseDto>> getTodosLosEstados() {
        return ResponseEntity.ok(estadoCompraService.getTodosLosEstados());
    }

    @GetMapping("/{id}")
    public ResponseEntity <EstadoCompraResponseDto> getEstadoById(@PathVariable Long id) {
        return ResponseEntity.ok(estadoCompraService.getEstadoById(id));
    }

}
