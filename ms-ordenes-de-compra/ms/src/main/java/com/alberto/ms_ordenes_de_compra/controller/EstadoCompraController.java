package com.albertolizana.ms_ordenes_de_compra.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
        List<EstadoCompraResponseDto> lec = estadoCompraService.getTodosLosEstados();
        lec.forEach(this::agregarLinksEstadoCompra);
        return ResponseEntity.ok(lec);
    }

    @GetMapping("/{id}")
    public ResponseEntity <EstadoCompraResponseDto> getEstadoById(@PathVariable Long id) {
        EstadoCompraResponseDto ec = estadoCompraService.getEstadoById(id);
        agregarLinksEstadoCompra(ec);
        return ResponseEntity.ok(ec);
    }

    private void agregarLinksEstadoCompra(EstadoCompraResponseDto estadoCompra){

        estadoCompra.add(linkTo(methodOn(EstadoCompraController.class)
                .getEstadoById(estadoCompra.getId()))
                .withSelfRel());
        
        estadoCompra.add(linkTo(methodOn(EstadoCompraController.class)
                .getTodosLosEstados())
                .withRel("collection"));
    
    }
}
