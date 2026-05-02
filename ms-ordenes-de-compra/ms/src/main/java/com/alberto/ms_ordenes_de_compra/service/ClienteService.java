package com.albertolizana.ms_ordenes_de_compra.service;

import java.util.List;

import com.albertolizana.ms_ordenes_de_compra.dto.ClienteResponseDto;

public interface ClienteService {

    public List<ClienteResponseDto> getClientes();
    public ClienteResponseDto getCliente(Long id);
    public void borrarLogico(Long id);
    public void borrarFisico(Long id);
    
}
