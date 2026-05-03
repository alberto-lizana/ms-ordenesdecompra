package com.albertolizana.ms_compra.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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


import com.albertolizana.ms_compra.dto.ClienteResponseDto;
import com.albertolizana.ms_compra.service.ClienteService;

@WebMvcTest(ClienteController.class)
@DisplayName("Pruebas de Integración - ClienteController")
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

	private ClienteResponseDto clienteUno;
	private ClienteResponseDto clienteDos;
	private List<ClienteResponseDto> usuarios;

	@BeforeEach
	void setUp() {
        clienteUno = ClienteResponseDto.builder()
                .idCliente(1L)
                .nombre("Alberto Lizana")
                .email("alberto@gmail.com")
                .estado(true)
                .build();

        clienteDos = ClienteResponseDto.builder()
                .idCliente(2L)
                .nombre("Juan Pérez")
                .email("juan@gmail.com")
                .estado(true)
                .build();	
		
		usuarios = new ArrayList<>();
		usuarios.add(clienteUno);
		usuarios.add(clienteDos);

	}

	@Test
	@DisplayName("GET /cliente/all - Debería retornar lista de clientes")
	void getClientes_deberiaRetornarListaDeUsuarios() throws Exception {

		when(clienteService.getClientes()).thenReturn(usuarios);

		mockMvc.perform(get("/cliente/all")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())

				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].idCliente").value(1))
				.andExpect(jsonPath("$[1].idCliente").value(2))

				.andExpect(jsonPath("$[0].nombre").value("Alberto Lizana"))
				.andExpect(jsonPath("$[1].nombre").value("Juan Pérez"));
		
		verify(clienteService, times(1)).getClientes();
	} 

	@Test
	@DisplayName("GET /cliente/{id} - Debería retornar un cliente en base a su id")
	void getCliente_deberiaRetornarUnClientePorSuId() throws Exception {

		when(clienteService.getCliente(1L)).thenReturn(clienteUno);

		mockMvc.perform(get("/cliente/{id}", 1)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())

				.andExpect(jsonPath("$.idCliente").value(1))
				.andExpect(jsonPath("$.nombre").value("Alberto Lizana"))
				.andExpect(jsonPath("$.email").value("alberto@gmail.com"))
				.andExpect(jsonPath("$.estado").value(true));

		verify(clienteService).getCliente(1L);
	}

	@Test
	@DisplayName("DELETE /cliente/borrar-logico/{id} - Debería borrar lógicamente a un cliente en base a su id")
	void borrarLogico_borrarClienteLogico() throws Exception {

		mockMvc.perform(delete("/cliente/borrar-logico/{id}", 2)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

		verify(clienteService).borrarLogico(2L);
		
	}

	@Test
	@DisplayName("DELETE /cliente/borrar-fisico/{id} - Debería borrar físicamente a un cliente en base a su id")
	void borrarFisico_borrarClienteFisicamenteEnLaBD() throws Exception {

		mockMvc.perform(delete("/cliente/borrar-fisico/{id}", 1)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
				
		verify(clienteService).borrarFisico(1L);
		
	}
}
