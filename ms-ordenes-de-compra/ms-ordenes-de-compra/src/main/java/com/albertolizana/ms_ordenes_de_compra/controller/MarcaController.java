package com.albertolizana.ms_ordenes_de_compra.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.albertolizana.ms_ordenes_de_compra.dto.MarcaResponseDto;
import com.albertolizana.ms_ordenes_de_compra.service.MarcaService;

@RestController
@RequestMapping("/marca")

public class MarcaController {

    private final MarcaService marcaService;

    public MarcaController(MarcaService marcaService){
        this.marcaService = marcaService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<MarcaResponseDto>> getMarcas(){
        return ResponseEntity.ok(marcaService.getMarcas());
    };

    @GetMapping("/{id}")
    public ResponseEntity<MarcaResponseDto> getMarca(@PathVariable Long id){
        return ResponseEntity.ok(marcaService.getMarca(id));
    };  
}