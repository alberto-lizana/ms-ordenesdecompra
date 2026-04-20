package com.albertolizana.ms_ordenes_de_compra.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.albertolizana.ms_ordenes_de_compra.dto.TipoProductoResponseDto;
import com.albertolizana.ms_ordenes_de_compra.service.TipoProductoService;

@RestController
@RequestMapping("/tipo_producto")

public class TipoProductoController {

    private final TipoProductoService tipoProductoService;

    public TipoProductoController(TipoProductoService tipoProductoService){
        this.tipoProductoService = tipoProductoService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<TipoProductoResponseDto>> getAllTipoProducto(){
        return ResponseEntity.ok(tipoProductoService.getAllTipoProducto());
    };

    @GetMapping("/{id}")
    public ResponseEntity<TipoProductoResponseDto> getTipoProducto(@PathVariable Long id){
        return ResponseEntity.ok(tipoProductoService.getTipoProducto(id));
    };
}
