package com.albertolizana.ms_ordenes_de_compra.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.albertolizana.ms_ordenes_de_compra.dto.DetalleCompraResponseDto;
import com.albertolizana.ms_ordenes_de_compra.service.DetalleCompraService;

@RestController
@RequestMapping("/detalle-compra")
public class DetalleCompraController {

    private final DetalleCompraService detalleCompraService;

    public DetalleCompraController(DetalleCompraService detalleCompraService) {
        this.detalleCompraService = detalleCompraService;
    }

    @GetMapping("/porCompra/{idCompra}")
    public ResponseEntity<List<DetalleCompraResponseDto>> getDetallesPorCompra(@PathVariable Long idCompra) {
        return ResponseEntity.ok(detalleCompraService.getDetallesPorCompra(idCompra));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleCompraResponseDto> getDetalle(@PathVariable Long id) {
        return ResponseEntity.ok(detalleCompraService.getDetalle(id));
    }
}