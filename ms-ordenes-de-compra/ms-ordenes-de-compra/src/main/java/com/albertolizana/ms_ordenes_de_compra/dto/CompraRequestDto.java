package com.albertolizana.ms_ordenes_de_compra.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class CompraRequestDto {

    @NotNull(message = "El id del cliente es obligatorio")
    private Long idCliente;

    @NotNull(message = "La lista de productos no puede ser nula")
    @Size(min = 1, message = "Debe haber al menos un producto en la compra")
    private List<ProductoCompraRequestDto> productos;
}
