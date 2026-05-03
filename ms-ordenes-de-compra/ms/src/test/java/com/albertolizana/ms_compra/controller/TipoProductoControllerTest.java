package com.albertolizana.ms_compra.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.albertolizana.ms_compra.dto.TipoProductoResponseDto;
import com.albertolizana.ms_compra.service.TipoProductoService;


@WebMvcTest(TipoProductoController.class)
@DisplayName("Pruebas de Integración - TipoProductoController")
public class TipoProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TipoProductoService tipoProductoService;

    private TipoProductoResponseDto productoUno;
    private TipoProductoResponseDto productoDos;
    private List<TipoProductoResponseDto> productos;

    @BeforeEach
    void setUp(){

        productoUno = TipoProductoResponseDto.builder()
                .idTipoProducto(1L)
                .nombre("ALIMENTO")
                .build();
        productoDos = TipoProductoResponseDto.builder()
                .idTipoProducto(2L)
                .nombre("HIGIENE")
                .build();

        productos = new ArrayList<>();
        productos.add(productoUno);
        productos.add(productoDos);

    }

    @Test
    @DisplayName("GET /tipo_producto/all - debería traer una lista de tipos de productos")
    void getAllTipoProducto_deberiaRetornarUnaListaDeTiposDeProductos() throws Exception {

        when(tipoProductoService.getAllTipoProducto()).thenReturn(productos);

        mockMvc.perform(get("/tipo_producto/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].nombre").value("ALIMENTO"))
				.andExpect(jsonPath("$[1].nombre").value("HIGIENE"))                
				.andExpect(jsonPath("$[0].idTipoProducto").value(1))
				.andExpect(jsonPath("$[1].idTipoProducto").value(2));

        verify(tipoProductoService, times(1)).getAllTipoProducto();
    } 

    @Test
    @DisplayName("GET /tipo_producto/{id} - debería traer una tipo de producto basado en su id")
    void getTipoProducto_deberíaRetornarUnTipoDeProducto() throws Exception {

        when(tipoProductoService.getTipoProducto(1L)).thenReturn(productoUno);

        mockMvc.perform(get("/tipo_producto/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                
                .andExpect(jsonPath("$.idTipoProducto").value(1))
                .andExpect(jsonPath("$.nombre").value("ALIMENTO"));

        verify(tipoProductoService).getTipoProducto(1L);

    } 
}
