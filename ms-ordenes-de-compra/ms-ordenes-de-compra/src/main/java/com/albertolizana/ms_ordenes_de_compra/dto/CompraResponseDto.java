package com.albertolizana.ms_ordenes_de_compra.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor        
@Getter
@Setter
@Builder
public class CompraResponseDto {

    private Long idCompra;
    private LocalDateTime fechaCompra;
    private ClienteResponseDto cliente;

}
