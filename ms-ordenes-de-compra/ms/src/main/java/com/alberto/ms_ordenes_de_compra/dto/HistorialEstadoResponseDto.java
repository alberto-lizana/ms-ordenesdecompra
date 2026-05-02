package com.albertolizana.ms_ordenes_de_compra.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

import org.springframework.hateoas.RepresentationModel;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor        
@Getter
@Setter
@Builder
public class HistorialEstadoResponseDto extends RepresentationModel<HistorialEstadoResponseDto> {

    private Long idHistorialEstados;
    private EstadoCompraResponseDto estadoCompra;
    private LocalDateTime fechaInicioEstado;
    private LocalDateTime fechaTerminoEstado;
    private CompraResponseDto compra;
}
