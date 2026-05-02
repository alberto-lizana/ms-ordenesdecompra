package com.albertolizana.ms_ordenes_de_compra.controller;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
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
        List<TipoProductoResponseDto> lista = tipoProductoService.getAllTipoProducto();
        lista.forEach(this::agregarLinksTipoProducto);        
        return ResponseEntity.ok(lista);
    };

    @GetMapping("/{id}")
    public ResponseEntity<TipoProductoResponseDto> getTipoProducto(@PathVariable Long id){
        TipoProductoResponseDto tp = tipoProductoService.getTipoProducto(id);
        agregarLinksTipoProducto(tp);
        return ResponseEntity.ok(tp);
    };

    private void agregarLinksTipoProducto(TipoProductoResponseDto tipoProducto){

        tipoProducto.add(linkTo(methodOn(TipoProductoController.class)
                .getTipoProducto(tipoProducto.getIdTipoProducto()))
                .withSelfRel());
        
        tipoProducto.add(linkTo(methodOn(TipoProductoController.class)
                .getAllTipoProducto())
                .withRel("collection"));         
    }
}
