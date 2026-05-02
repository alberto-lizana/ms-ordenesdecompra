package com.albertolizana.ms_compra.controller;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.albertolizana.ms_compra.dto.DetalleCompraResponseDto;
import com.albertolizana.ms_compra.service.DetalleCompraService;



@RestController
@RequestMapping("/detalle-compra")
public class DetalleCompraController {

    private final DetalleCompraService detalleCompraService;

    public DetalleCompraController(DetalleCompraService detalleCompraService) {
        this.detalleCompraService = detalleCompraService;
    }

    @GetMapping("/porCompra/{idCompra}")
    public ResponseEntity<List<DetalleCompraResponseDto>> getDetallesPorCompra(@PathVariable Long idCompra) {
        List<DetalleCompraResponseDto> ldc = detalleCompraService.getDetallesPorCompra(idCompra);
        ldc.forEach(t -> agregarLinksDetalleCompra(t, idCompra));
        return ResponseEntity.ok(ldc);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleCompraResponseDto> getDetalle(@PathVariable Long id) {
        DetalleCompraResponseDto dc = detalleCompraService.getDetalle(id);
        agregarLinksDetalleCompra(dc, id);
        return ResponseEntity.ok(dc);
    }

    private void agregarLinksDetalleCompra(DetalleCompraResponseDto detalleCompra, Long idCompra){

        detalleCompra.add(linkTo(methodOn(DetalleCompraController.class)
                .getDetalle(detalleCompra.getIdDetalleCompra()))
                .withSelfRel());
        
        detalleCompra.add(linkTo(methodOn(DetalleCompraController.class)
                .getDetallesPorCompra(idCompra))
                .withRel("compra-detalle-collection"));
    }
}