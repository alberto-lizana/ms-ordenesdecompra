package com.albertolizana.ms_ordenes_de_compra.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor        
@Getter
@Setter
@Builder
public class CrearNuevoHistorialRequestDto {

    @NotNull(message = "El id de compra es obligatorio")
    private Long idCompra;
    
    @NotNull(message = "El id del estado de compra es obligatorio")
    private boolean esAvance; // true para avanzar, false para cancelar

}