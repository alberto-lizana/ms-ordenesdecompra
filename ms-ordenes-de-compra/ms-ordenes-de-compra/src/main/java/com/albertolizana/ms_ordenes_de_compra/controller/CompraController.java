package com.albertolizana.ms_ordenes_de_compra.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.albertolizana.ms_ordenes_de_compra.dto.CompraRequestDto;
import com.albertolizana.ms_ordenes_de_compra.dto.CompraResponseDto;
import com.albertolizana.ms_ordenes_de_compra.service.CompraService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/compra")
public class CompraController {

    private final CompraService compraService;

    public CompraController(CompraService compraService)
    {
        this.compraService = compraService;
    };

    @GetMapping("/all")
    public ResponseEntity<List<CompraResponseDto>> getCompras(){
        return ResponseEntity.ok(compraService.getCompras());
    };

    @GetMapping("/{id}")
    public ResponseEntity<CompraResponseDto> getCompra(@PathVariable Long id){
        return ResponseEntity.ok(compraService.getCompra(id));
    };

    @PostMapping("/realizar")
    public ResponseEntity<CompraResponseDto> crearCompra(@Valid @RequestBody CompraRequestDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(compraService.crearCompra(dto));
    };
}
