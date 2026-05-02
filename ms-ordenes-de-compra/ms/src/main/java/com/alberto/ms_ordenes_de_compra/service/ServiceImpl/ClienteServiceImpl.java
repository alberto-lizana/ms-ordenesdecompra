package com.albertolizana.ms_ordenes_de_compra.service.ServiceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.albertolizana.ms_ordenes_de_compra.dto.ClienteResponseDto;
import com.albertolizana.ms_ordenes_de_compra.exception.ResourceNotFoundException;
import com.albertolizana.ms_ordenes_de_compra.model.Cliente;
import com.albertolizana.ms_ordenes_de_compra.repository.ClienteRepository;
import com.albertolizana.ms_ordenes_de_compra.service.ClienteService;

@Service
@Transactional
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository)
    {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public List<ClienteResponseDto> getClientes() {
        return clienteRepository.findAll()
                            .stream()
                            .map(c -> ClienteResponseDto.builder()
                                .idCliente(c.getIdCliente())
                                .nombre(c.getNombre())
                                .email(c.getEmail())
                                .estado(c.isEstado())
                                .build())
                            .toList();
    }

    @Override
    public ClienteResponseDto getCliente(Long id) {
        return clienteRepository.findById(id)
                            .map(c -> ClienteResponseDto.builder()
                                .idCliente(c.getIdCliente())
                                .nombre(c.getNombre())
                                .email(c.getEmail())
                                .estado(c.isEstado())
                            .build())
                            .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado el paciente con id: " + id));
    }

    @Override
    public void borrarLogico(Long id) {
        Cliente c = clienteRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado el paciente con id: " + id));

        c.setEstado(false);
        clienteRepository.save(c);
    }

    @Override
    public void borrarFisico(Long id) {
        if(!clienteRepository.existsById(id)){
            throw new ResourceNotFoundException("No se ha encontrado el paciente con id: " + id);
        }
        clienteRepository.deleteById(id);
    }

}
