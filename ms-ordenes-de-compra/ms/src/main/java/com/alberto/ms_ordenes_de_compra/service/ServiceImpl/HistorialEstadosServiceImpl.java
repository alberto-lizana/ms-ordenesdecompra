package com.albertolizana.ms_ordenes_de_compra.service.ServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.albertolizana.ms_ordenes_de_compra.dto.ClienteResponseDto;
import com.albertolizana.ms_ordenes_de_compra.dto.CompraResponseDto;
import com.albertolizana.ms_ordenes_de_compra.dto.CrearNuevoHistorialRequestDto;
import com.albertolizana.ms_ordenes_de_compra.dto.EstadoCompraResponseDto;
import com.albertolizana.ms_ordenes_de_compra.dto.HistorialEstadoResponseDto;
import com.albertolizana.ms_ordenes_de_compra.exception.ResourceNotFoundException;
import com.albertolizana.ms_ordenes_de_compra.model.EstadoCompra;
import com.albertolizana.ms_ordenes_de_compra.model.HistorialEstados;
import com.albertolizana.ms_ordenes_de_compra.repository.CompraRepository;
import com.albertolizana.ms_ordenes_de_compra.repository.EstadoCompraRepository;
import com.albertolizana.ms_ordenes_de_compra.repository.HistorialEstadosRepository;
import com.albertolizana.ms_ordenes_de_compra.service.HistorialEstadosService;

@Service
@Transactional
public class HistorialEstadosServiceImpl implements HistorialEstadosService {

    private final HistorialEstadosRepository historialEstadosRepository;
    private final CompraRepository compraRepository;
    private final EstadoCompraRepository estadoCompraRepository;

    public HistorialEstadosServiceImpl
    (
        HistorialEstadosRepository historialEstadosRepository, CompraRepository compraRepository,
        EstadoCompraRepository estadoCompraRepository
    )
    {
        this.historialEstadosRepository = historialEstadosRepository;
        this.compraRepository = compraRepository;
        this.estadoCompraRepository = estadoCompraRepository;
    }

    @Override
    public List<HistorialEstadoResponseDto> getTodosHistoriales() {
        return historialEstadosRepository.findAllWithRelations()
                                    .stream()
                                    .map(he -> HistorialEstadoResponseDto
                                        .builder()
                                        .idHistorialEstados(he.getIdHistorialEstados())
                                        .fechaInicioEstado(he.getFechaInicioEstado())
                                        .fechaTerminoEstado(he.getFechaTerminoEstado())

                                        .estadoCompra(EstadoCompraResponseDto
                                            .builder()
                                            .id(he.getEstadoCompra().getId())
                                            .estado(he.getEstadoCompra().getNombreEstado())
                                        .build())    
                                                                       
                                        .compra(CompraResponseDto
                                            .builder()
                                            .idCompra(he.getCompra().getIdCompra())
                                            .fechaCompra(he.getCompra().getFechaCompra())
                                            
                                            .cliente(ClienteResponseDto
                                                .builder()
                                                .idCliente(he.getCompra().getCliente().getIdCliente())
                                                .nombre(he.getCompra().getCliente().getNombre())
                                                .email(he.getCompra().getCliente().getEmail())
                                                .estado(he.getCompra().getCliente().isEstado())
                                            .build())
                                        .build())
                                    .build())
                                .toList();

    }

    @Override
    public HistorialEstadoResponseDto getHistorialById(Long id) {
        return historialEstadosRepository.findWithRelations(id)
                                    .map(he -> HistorialEstadoResponseDto
                                        .builder()
                                        .idHistorialEstados(he.getIdHistorialEstados())
                                        .fechaInicioEstado(he.getFechaInicioEstado())
                                        .fechaTerminoEstado(he.getFechaTerminoEstado())
                                        
                                        .estadoCompra(EstadoCompraResponseDto
                                            .builder()
                                            .id(he.getEstadoCompra().getId())
                                            .estado(he.getEstadoCompra().getNombreEstado())
                                        .build())

                                        .compra(CompraResponseDto
                                            .builder()
                                            .idCompra(he.getCompra().getIdCompra())
                                            .fechaCompra(he.getCompra().getFechaCompra())
                                            
                                            .cliente(ClienteResponseDto
                                                .builder()
                                                .idCliente(he.getCompra().getCliente().getIdCliente())
                                                .nombre(he.getCompra().getCliente().getNombre())
                                                .email(he.getCompra().getCliente().getEmail())
                                                .estado(he.getCompra().getCliente().isEstado())
                                            .build())
                                        .build())
                                    .build())
                                    .orElseThrow(() -> new ResourceNotFoundException("No se encontró el historial con id " + id));
    }

    @Override
    public HistorialEstadoResponseDto crearEstadoHistorial(CrearNuevoHistorialRequestDto dto) {

        if (!compraRepository.existsById(dto.getIdCompra())) {
            throw new ResourceNotFoundException("No se ha encontrado la compra con id " + dto.getIdCompra());
        } 

        HistorialEstados ultimoHistorial = historialEstadosRepository.findByFechaTerminoEstadoIsNullAndCompra_IdCompra(dto.getIdCompra())
                                                        .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado un historial activo para la compra con id " + dto.getIdCompra()));
        

        String estadoActual = ultimoHistorial.getEstadoCompra().getNombreEstado();

        if (estadoActual.equals("ENTREGADO") || estadoActual.equals("CANCELADO")) {
        throw new IllegalStateException(
            "No se puede modificar el estado de una compra que ya ha sido entregada o cancelada");
        }

        // Determinar el nuevo estado
        EstadoCompra nuevoEstado;
        if (dto.isEsAvance()) {
            nuevoEstado = obtenerSiguienteEstado(estadoActual);
        } else {
            nuevoEstado = estadoCompraRepository.findByNombreEstado("CANCELADO")
                    .orElseThrow(() -> new ResourceNotFoundException("Estado CANCELADO no encontrado"));
        }

        // Obtener Consistencia de fechas entre los dos registors cuando uno se cierra (fecha termino no null) y el otro se abre (fecha termino null)
        LocalDateTime ahora = LocalDateTime.now();

        // Cerrar historial actual
        ultimoHistorial.setFechaTerminoEstado(ahora);
        historialEstadosRepository.save(ultimoHistorial);

        // Crear nuevo historial
        HistorialEstados nuevoHistorial = HistorialEstados.builder()
                                                    .compra(ultimoHistorial.getCompra())
                                                    .estadoCompra(nuevoEstado)
                                                    .fechaInicioEstado(ahora)
                                                    .fechaTerminoEstado(nuevoEstado.getNombreEstado().equals("CANCELADO") ? ahora : null)
                                                    .build();

        historialEstadosRepository.save(nuevoHistorial);

        // Retornar respuesta
        return HistorialEstadoResponseDto.builder()
                                    .idHistorialEstados(nuevoHistorial.getIdHistorialEstados())
                                    .fechaInicioEstado(nuevoHistorial.getFechaInicioEstado())
                                    .estadoCompra(EstadoCompraResponseDto.builder()
                                        .id(nuevoEstado.getId())
                                        .estado(nuevoEstado.getNombreEstado())
                                        .build())
                                    .build();
    }


    @Override
    public List<HistorialEstadoResponseDto> getTodosLosHistorialesActuales() {
        return historialEstadosRepository.findAllByFechaTerminoEstadoNull()
                                                            .stream()
                                                            .map(h -> HistorialEstadoResponseDto
                                                                .builder()
                                                                .idHistorialEstados(h.getIdHistorialEstados())
                                                                .fechaInicioEstado(h.getFechaInicioEstado())
                                                                .fechaTerminoEstado(h.getFechaTerminoEstado())

                                                                .estadoCompra(EstadoCompraResponseDto
                                                                    .builder()
                                                                    .id(h.getEstadoCompra().getId())
                                                                    .estado(h.getEstadoCompra().getNombreEstado())
                                                                .build())
                                                                
                                                                .compra(CompraResponseDto
                                                                    .builder()
                                                                    .idCompra(h.getCompra().getIdCompra())
                                                                    .fechaCompra(h.getCompra().getFechaCompra())
                                            
                                                                    .cliente(ClienteResponseDto
                                                                        .builder()
                                                                        .idCliente(h.getCompra().getCliente().getIdCliente())
                                                                        .nombre(h.getCompra().getCliente().getNombre())
                                                                        .email(h.getCompra().getCliente().getEmail())
                                                                        .estado(h.getCompra().getCliente().isEstado())
                                                                    .build())
                                                                .build())
                                                            .build())
                                                        .toList();

    }

    @Override
    public List<HistorialEstadoResponseDto> getHistoricoCompra(Long idCompra) {
        return historialEstadosRepository.findAllByCompra_idCompra(idCompra)
                                                            .stream()
                                                            .map(h -> HistorialEstadoResponseDto
                                                                .builder()
                                                                .idHistorialEstados(h.getIdHistorialEstados())
                                                                .fechaInicioEstado(h.getFechaInicioEstado())
                                                                .fechaTerminoEstado(h.getFechaTerminoEstado())

                                                                .estadoCompra(EstadoCompraResponseDto
                                                                    .builder()
                                                                    .id(h.getEstadoCompra().getId())
                                                                    .estado(h.getEstadoCompra().getNombreEstado())
                                                                .build())
                                                                
                                                                .compra(CompraResponseDto
                                                                    .builder()
                                                                    .idCompra(h.getCompra().getIdCompra())
                                                                    .fechaCompra(h.getCompra().getFechaCompra())
                                            
                                                                    .cliente(ClienteResponseDto
                                                                        .builder()
                                                                        .idCliente(h.getCompra().getCliente().getIdCliente())
                                                                        .nombre(h.getCompra().getCliente().getNombre())
                                                                        .email(h.getCompra().getCliente().getEmail())
                                                                        .estado(h.getCompra().getCliente().isEstado())
                                                                    .build())
                                                                .build())
                                                            .build())
                                                        .toList();
    }

     // Método para la progresión de estados
    private EstadoCompra obtenerSiguienteEstado(String estadoActual) {
        String siguiente = switch (estadoActual) {
            case "PREPARACION"  -> "DESPACHABLE";
            case "DESPACHABLE"  -> "EN TRANSITO";
            case "EN TRANSITO"  -> "ENTREGADO";
            default -> throw new IllegalStateException("Estado desconocido: " + estadoActual);
        };
        return estadoCompraRepository.findByNombreEstado(siguiente)
                .orElseThrow(() -> new ResourceNotFoundException("Estado " + siguiente + " no encontrado"));
    }
}
