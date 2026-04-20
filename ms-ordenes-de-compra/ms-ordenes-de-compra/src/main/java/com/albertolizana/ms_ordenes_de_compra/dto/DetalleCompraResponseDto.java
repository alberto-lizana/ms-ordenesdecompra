package com.albertolizana.ms_ordenes_de_compra.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DetalleCompraResponseDto {

    private Long idDetalleCompra;
    private Integer cantidad;
    private Integer precioUnitario;
    private Integer subtotal; 
    private ProductoResponseDto producto;

}