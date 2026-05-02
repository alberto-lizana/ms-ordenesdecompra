package com.albertolizana.ms_ordenes_de_compra.dto;

import jakarta.validation.constraints.Min;
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
public class ProductoRequestDto {

    @NotNull(message="El id del producto no puede ser null")
    private Long idProducto;
    private String nombre;

    @Min(value = 1, message = "El stock no puede ser menor a 1")
    private Integer stock;

    @Min(value = 1, message = "El precio no puede ser menor a 1")
    private Integer precio;
    private Long idTipoProducto;
    private Long idMarca;

}
