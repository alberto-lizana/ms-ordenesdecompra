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
public class ProductoResponseDto extends RepresentationModel<ProductoResponseDto> {

    private Long idProducto;
    private String nombre;
    private Integer stock;
    private Integer precio;
    private TipoProductoResponseDto tipoProducto;
    private MarcaResponseDto marca;

}