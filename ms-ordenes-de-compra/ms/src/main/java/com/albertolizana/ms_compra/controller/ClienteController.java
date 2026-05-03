package com.albertolizana.ms_compra.controller;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.albertolizana.ms_compra.dto.ClienteResponseDto;
import com.albertolizana.ms_compra.service.ClienteService;


@RestController
@RequestMapping("/cliente")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService){
        this.clienteService = clienteService;
    };

    @GetMapping("/all")
    public ResponseEntity<List<ClienteResponseDto>> getClientes(){ 
        List<ClienteResponseDto> lc = clienteService.getClientes();
        lc.forEach(t -> agregarLinksCliente(t));
        return ResponseEntity.ok(lc);
    };

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> getCliente(@PathVariable Long id){ 
        ClienteResponseDto c = clienteService.getCliente(id);
        agregarLinksCliente(c);
        return ResponseEntity.ok(c);
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

    private void agregarLinksCliente(ClienteResponseDto cliente){

        cliente.add(linkTo(methodOn(ClienteController.class)
                .getCliente(cliente.getIdCliente()))
                .withSelfRel());

        cliente.add(linkTo(methodOn(ClienteController.class)
                .getClientes())
                .withRel("collection"));
    }
}


