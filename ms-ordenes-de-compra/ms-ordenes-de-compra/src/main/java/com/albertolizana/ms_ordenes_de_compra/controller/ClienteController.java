package com.albertolizana.ms_ordenes_de_compra.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.albertolizana.ms_ordenes_de_compra.dto.ClienteResponseDto;
import com.albertolizana.ms_ordenes_de_compra.service.ClienteService;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService){
        this.clienteService = clienteService;
    };

    @GetMapping("/all")
    public ResponseEntity<List<ClienteResponseDto>> getClientes(){ 
        return ResponseEntity.ok(clienteService.getClientes());
    };

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> getCliente(@PathVariable Long id){ 
        return ResponseEntity.ok(clienteService.getCliente(id));
    };    

    @DeleteMapping("/borrar-logico/{id}")
    public ResponseEntity<?> borrarClienteLogico(@PathVariable Long id){
        clienteService.borrarLogico(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/borrar-fisico/{id}")
    public ResponseEntity<?> borrarClienteFisico(@PathVariable Long id){
        clienteService.borrarFisico(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
