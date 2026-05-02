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
public class ProductoCompraRequestDto {

    @NotNull(message = "El id del producto es obligatorio")
    private Long idProducto;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad tiene que ser un número mayor o igual a 1")
    private Integer cantidad;

}
