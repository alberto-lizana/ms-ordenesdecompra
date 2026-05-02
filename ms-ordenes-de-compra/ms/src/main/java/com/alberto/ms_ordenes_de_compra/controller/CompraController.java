package com.albertolizana.ms_ordenes_de_compra.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


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
        List<CompraResponseDto> lc = compraService.getCompras();
        lc.forEach(this::agregarLinksCompra);
        return ResponseEntity.ok(lc);
    };

    @GetMapping("/{id}")
    public ResponseEntity<CompraResponseDto> getCompra(@PathVariable Long id){
        CompraResponseDto c = compraService.getCompra(id);
        agregarLinksCompra(c);
        return ResponseEntity.ok(c);
    };

    @PostMapping("/realizar")
    public ResponseEntity<CompraResponseDto> crearCompra(@Valid @RequestBody CompraRequestDto dto){
        CompraResponseDto c = compraService.crearCompra(dto);
        agregarLinksCompra(c);
        return ResponseEntity.status(HttpStatus.CREATED).body(c);
    };

    private void agregarLinksCompra(CompraResponseDto compra){

        compra.add(linkTo(methodOn(CompraController.class)
            .getCompra(compra.getIdCompra()))
            .withSelfRel());
        
        compra.add(linkTo(methodOn(CompraController.class)
            .getCompras())
            .withRel("collection"));

        compra.add(linkTo(methodOn(CompraController.class)
            .crearCompra(null))
            .withRel("create"));  
    }
}
