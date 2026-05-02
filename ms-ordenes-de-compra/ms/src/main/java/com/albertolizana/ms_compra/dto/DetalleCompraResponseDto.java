package com.albertolizana.ms_compra.dto;

import org.springframework.hateoas.RepresentationModel;

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
public class DetalleCompraResponseDto extends RepresentationModel<DetalleCompraResponseDto>{

    private Long idDetalleCompra;
    private Integer cantidad;
    private Integer precioUnitario;
    private Integer subtotal; 
    private ProductoResponseDto producto;

}