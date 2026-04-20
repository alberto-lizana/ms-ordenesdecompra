package com.albertolizana.ms_ordenes_de_compra.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor        
@Getter
@Setter
@Builder
public class ClienteResponseDto {

    private Long idCliente;
    private String nombre;
    private String email;
    private boolean estado;

}
