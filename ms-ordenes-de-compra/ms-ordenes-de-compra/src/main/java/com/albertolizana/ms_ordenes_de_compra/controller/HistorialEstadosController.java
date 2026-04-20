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
        return ResponseEntity.ok(historialEstadosService.getTodosHistoriales());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistorialEstadoResponseDto> getHistorialById(@PathVariable Long id){
        return ResponseEntity.ok(historialEstadosService.getHistorialById(id));
    };

    @GetMapping("/historiales_actuales")
    public ResponseEntity<List<HistorialEstadoResponseDto>> getTodosLosHistorialesActuales(){
        return ResponseEntity.ok(historialEstadosService.getTodosLosHistorialesActuales());
    };

    @GetMapping("/historialesPorCompra/{idCompra}")
    public ResponseEntity<List<HistorialEstadoResponseDto>> getHistoricoCompra(@PathVariable Long idCompra){
        return ResponseEntity.ok(historialEstadosService.getHistoricoCompra(idCompra));
    };    

    @PostMapping("/crear")
    public ResponseEntity<HistorialEstadoResponseDto> crearEstadoHistorial(@Valid @RequestBody CrearNuevoHistorialRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(historialEstadosService.crearEstadoHistorial(dto));
    }

}