package com.albertolizana.ms_compra.controller;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.albertolizana.ms_compra.dto.ProductoRequestDto;
import com.albertolizana.ms_compra.dto.ProductoResponseDto;
import com.albertolizana.ms_compra.service.ProductoService;

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
        List<ProductoResponseDto> lp = productoService.getProductos();
        lp.forEach(this::agregarLinksProducto);
        return ResponseEntity.ok(lp);
    };

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDto> getProducto(@PathVariable Long id){
        ProductoResponseDto p = productoService.getProducto(id);
        agregarLinksProducto(p);
        return ResponseEntity.ok(p);
    };

    @PutMapping("/modificar")
    public ResponseEntity<ProductoResponseDto> modificarProducto(@Valid @RequestBody ProductoRequestDto dto){
        ProductoResponseDto p = productoService.modificarProducto(dto);
        return ResponseEntity.ok(p);
    }

    public void agregarLinksProducto(ProductoResponseDto producto){

        producto.add(linkTo(methodOn(ProductoController.class)
            .getProducto(producto.getIdProducto()))
            .withSelfRel());
        
        producto.add(linkTo(methodOn(ProductoController.class)
            .getProductos())
            .withRel("collection"));

    }
}
