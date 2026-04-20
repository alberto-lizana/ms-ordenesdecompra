package com.albertolizana.ms_ordenes_de_compra.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.albertolizana.ms_ordenes_de_compra.dto.ProductoRequestDto;
import com.albertolizana.ms_ordenes_de_compra.dto.ProductoResponseDto;
import com.albertolizana.ms_ordenes_de_compra.service.ProductoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService){
        this.productoService = productoService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductoResponseDto>> getProductos(){
        return ResponseEntity.ok(productoService.getProductos());
    };

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDto> getProducto(@PathVariable Long id){
        return ResponseEntity.ok(productoService.getProducto(id));
    };

    @PutMapping("/modificar")
    public ResponseEntity<ProductoResponseDto> modificarProducto(@Valid @RequestBody ProductoRequestDto dto){
        return ResponseEntity.ok(productoService.modificarProducto(dto));
    }
}
