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

import com.albertolizana.ms_ordenes_de_compra.dto.CrearNuevoHistorialRequestDto;
import com.albertolizana.ms_ordenes_de_compra.dto.HistorialEstadoResponseDto;
import com.albertolizana.ms_ordenes_de_compra.service.HistorialEstadosService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/historial_estado")

public class HistorialEstadosController {

    private final HistorialEstadosService historialEstadosService;

    public HistorialEstadosController(HistorialEstadosService historialEstadosService)
    {
        this.historialEstadosService = historialEstadosService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<HistorialEstadoResponseDto>> getTodosHistoriales() {
        List<HistorialEstadoResponseDto> lhe = historialEstadosService.getTodosHistoriales();
        lhe.forEach(this::agregarLinksHistorialEstados);
        return ResponseEntity.ok(lhe);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistorialEstadoResponseDto> getHistorialById(@PathVariable Long id){
        HistorialEstadoResponseDto he = historialEstadosService.getHistorialById(id);
        agregarLinksHistorialEstados(he);
        return ResponseEntity.ok(he);
    };

    @GetMapping("/historiales_actuales")
    public ResponseEntity<List<HistorialEstadoResponseDto>> getTodosLosHistorialesActuales(){
        List<HistorialEstadoResponseDto> lhe = historialEstadosService.getTodosLosHistorialesActuales();
        lhe.forEach(this::agregarLinksHistorialEstados); 
        return ResponseEntity.ok(lhe);
    };

    @GetMapping("/historialesPorCompra/{idCompra}")
    public ResponseEntity<List<HistorialEstadoResponseDto>> getHistoricoCompra(@PathVariable Long idCompra){
        List<HistorialEstadoResponseDto> lhe = historialEstadosService.getHistoricoCompra(idCompra);
        lhe.forEach(this::agregarLinksHistorialEstados); 
        return ResponseEntity.ok(lhe);
    };    

    @PostMapping("/crear")
    public ResponseEntity<HistorialEstadoResponseDto> crearEstadoHistorial(@Valid @RequestBody CrearNuevoHistorialRequestDto dto) {
        HistorialEstadoResponseDto he = historialEstadosService.crearEstadoHistorial(dto);
        agregarLinksHistorialEstados(he);
        return ResponseEntity.status(HttpStatus.CREATED).body(he);
    }

    private void agregarLinksHistorialEstados(HistorialEstadoResponseDto historialEstado){

        historialEstado.add(linkTo(methodOn(HistorialEstadosController.class)
                .getHistorialById(historialEstado.getIdHistorialEstados()))
                .withSelfRel());
        
        historialEstado.add(linkTo(methodOn(HistorialEstadosController.class)
                .getTodosHistoriales())
                .withRel("collection"));
            
        historialEstado.add(linkTo(methodOn(HistorialEstadosController.class)
                .getTodosLosHistorialesActuales())
                .withRel("current-collection"));
        
        historialEstado.add(linkTo(methodOn(HistorialEstadosController.class)
                .getHistoricoCompra(historialEstado.getCompra().getIdCompra()))
                .withRel("compra-historial"));
        
        historialEstado.add(linkTo(methodOn(HistorialEstadosController.class)
                .crearEstadoHistorial(null))
                .withRel("create"));
    }

}
