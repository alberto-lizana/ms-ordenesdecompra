package com.albertolizana.ms_ordenes_de_compra.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
        List<MarcaResponseDto> lm = marcaService.getMarcas();
        lm.forEach(this::agregarLinksMarca);
        return ResponseEntity.ok(lm);
    };

    @GetMapping("/{id}")
    public ResponseEntity<MarcaResponseDto> getMarca(@PathVariable Long id){
        MarcaResponseDto m = marcaService.getMarca(id);
        agregarLinksMarca(m);
        return ResponseEntity.ok(m);
    }; 

    private void agregarLinksMarca(MarcaResponseDto marca){

        marca.add(linkTo(methodOn(MarcaController.class)
            .getMarca(marca.getIdMarca()))
            .withSelfRel());  

        marca.add(linkTo(methodOn(MarcaController.class)
            .getMarcas())
            .withRel("collection"));
    }
}
