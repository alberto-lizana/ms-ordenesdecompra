package com.albertolizana.ms_compra.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.albertolizana.ms_compra.dto.ClienteResponseDto;
import com.albertolizana.ms_compra.dto.CompraRequestDto;
import com.albertolizana.ms_compra.dto.CompraResponseDto;
import com.albertolizana.ms_compra.dto.ProductoCompraRequestDto;
import com.albertolizana.ms_compra.service.CompraService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CompraController.class)
@DisplayName("Pruebas de Integración - CompraController")
public class CompraControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CompraService compraService;


    // Lista de Productos ficticia
    private List<ProductoCompraRequestDto> productosUno;

    private List<ProductoCompraRequestDto> productosDos;

    private List<ProductoCompraRequestDto> productosTres;
    
    // Request
    private CompraRequestDto pedidoUno;
    private CompraRequestDto pedidoDos;
    private CompraRequestDto pedidoTres;

    // Response
	private CompraResponseDto responseCompraUno;
	private CompraResponseDto responseCompraDos;
	private List<CompraResponseDto> compras;    

    
    @BeforeEach
    void setUp(){

        objectMapper.findAndRegisterModules();
        
        productosUno = new ArrayList<>();

        productosUno.add(ProductoCompraRequestDto.builder()
                .idProducto(1L)
                .cantidad(10)
                .build());
    
        productosUno.add(ProductoCompraRequestDto.builder()
                .idProducto(5L)
                .cantidad(5)
                .build());

        productosUno.add(ProductoCompraRequestDto.builder()
                .idProducto(13L)
                .cantidad(7)
                .build());

        productosUno.add(ProductoCompraRequestDto.builder()
                .idProducto(7L)
                .cantidad(1)
                .build());

        productosDos = new ArrayList<>();
        productosTres = new ArrayList<>();

        productosTres.add(ProductoCompraRequestDto.builder()
                .idProducto(11L)
                .cantidad(1)
                .build());
    
        productosTres.add(ProductoCompraRequestDto.builder()
                .idProducto(15L)
                .cantidad(3)
                .build());

        productosTres.add(ProductoCompraRequestDto.builder()
                .idProducto(3L)
                .cantidad(7)
                .build());

        productosTres.add(ProductoCompraRequestDto.builder()
                .idProducto(9L)
                .cantidad(1)
                .build());        


        pedidoUno = CompraRequestDto.builder()
                .idCliente(1L)
                .productos(productosUno)
                .build();

        pedidoDos = CompraRequestDto.builder()
                .idCliente(1L)
                .productos(productosDos)
                .build();                

        pedidoTres = CompraRequestDto.builder()
                .idCliente(2L)
                .productos(productosTres)
                .build();

        responseCompraUno = CompraResponseDto.builder()
                .idCompra(1L)
                .fechaCompra(LocalDateTime.now())
                .cliente(ClienteResponseDto
                    .builder()
                    .idCliente(1L)
                    .nombre("Alberto")
                    .email("Alberto@gmail.com")
                    .estado(true)
                    .build())
                .build();

        responseCompraDos = CompraResponseDto.builder()
                .idCompra(2L)
                .fechaCompra(LocalDateTime.now())
                .cliente(ClienteResponseDto
                    .builder()
                    .idCliente(2L)
                    .nombre("Felipe")
                    .email("Felipe@gmail.com")
                    .estado(true)
                    .build())
                .build();


        compras = new ArrayList<>();
        compras.add(responseCompraUno);
        compras.add(responseCompraDos);

    }

    @Test
    @DisplayName("POST /compra/realizar - debería crear compra")
    void crearCompra_deberiaRetornarCompraCreada() throws Exception {

        when(compraService.crearCompra(any(CompraRequestDto.class)))
                .thenReturn(responseCompraUno);

        mockMvc.perform(post("/compra/realizar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pedidoUno)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idCompra").value(1));
        verify(compraService).crearCompra(any(CompraRequestDto.class));
    }

   @Test
   @DisplayName("POST /compra/realizar - debería retornar 400 si la lista de productos está vacía")
   void crearCompra_deberiaRetornar400() throws Exception {

        mockMvc.perform(post("/compra/realizar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pedidoDos))) 
                .andExpect(status().isBadRequest());

        verify(compraService, times(0)).crearCompra(any());
   }
}
