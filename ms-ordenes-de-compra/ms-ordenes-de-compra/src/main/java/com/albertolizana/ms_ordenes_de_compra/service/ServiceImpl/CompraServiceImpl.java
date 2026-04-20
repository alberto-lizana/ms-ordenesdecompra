package com.albertolizana.ms_ordenes_de_compra.service.ServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.albertolizana.ms_ordenes_de_compra.dto.ClienteResponseDto;
import com.albertolizana.ms_ordenes_de_compra.dto.CompraRequestDto;
import com.albertolizana.ms_ordenes_de_compra.dto.CompraResponseDto;
import com.albertolizana.ms_ordenes_de_compra.dto.ProductoCompraRequestDto;
import com.albertolizana.ms_ordenes_de_compra.exception.ResourceNotFoundException;
import com.albertolizana.ms_ordenes_de_compra.exception.StockInsuficienteException;
import com.albertolizana.ms_ordenes_de_compra.model.Cliente;
import com.albertolizana.ms_ordenes_de_compra.model.Compra;
import com.albertolizana.ms_ordenes_de_compra.model.DetalleCompra;
import com.albertolizana.ms_ordenes_de_compra.model.HistorialEstados;
import com.albertolizana.ms_ordenes_de_compra.model.Producto;
import com.albertolizana.ms_ordenes_de_compra.repository.ClienteRepository;
import com.albertolizana.ms_ordenes_de_compra.repository.CompraRepository;
import com.albertolizana.ms_ordenes_de_compra.repository.DetalleCompraRepository;
import com.albertolizana.ms_ordenes_de_compra.repository.EstadoCompraRepository;
import com.albertolizana.ms_ordenes_de_compra.repository.HistorialEstadosRepository;
import com.albertolizana.ms_ordenes_de_compra.repository.ProductoRepository;
import com.albertolizana.ms_ordenes_de_compra.service.CompraService;

@Service
@Transactional
public class CompraServiceImpl implements CompraService {

    private final CompraRepository compraRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;
    private final DetalleCompraRepository detalleCompraRepository;
    private final EstadoCompraRepository estadoCompraRepository;
    private final HistorialEstadosRepository historialEstadosRepository;

    public CompraServiceImpl
    (
        CompraRepository compraRepository, ClienteRepository clienteRepository, 
        ProductoRepository productoRepository, DetalleCompraRepository detalleCompraRepository,
        EstadoCompraRepository estadoCompraRepository, HistorialEstadosRepository historialEstadosRepository
    )
    {
        this.compraRepository = compraRepository;
        this.clienteRepository = clienteRepository;
        this.productoRepository = productoRepository;
        this.detalleCompraRepository = detalleCompraRepository;
        this.estadoCompraRepository = estadoCompraRepository;
        this.historialEstadosRepository = historialEstadosRepository;
    }

    @Override
    public List<CompraResponseDto> getCompras() {
        return compraRepository.findAll()
                            .stream()
                            .map(c -> CompraResponseDto
                                .builder()
                                .idCompra(c.getIdCompra())
                                .fechaCompra(c.getFechaCompra())
                            .cliente(ClienteResponseDto
                                .builder()
                                .idCliente(c.getCliente().getIdCliente())
                                .nombre(c.getCliente().getNombre())
                                .email(c.getCliente().getEmail())
                                .estado(c.getCliente()
                                .isEstado())
                                .build())
                            .build())
                            .toList();

    }

    @Override
    public CompraResponseDto getCompra(Long id) {
        return compraRepository.findById(id)
                            .map(c -> CompraResponseDto
                                .builder()
                                .idCompra(c.getIdCompra())
                                .fechaCompra(c.getFechaCompra())
                            .cliente(ClienteResponseDto
                                .builder()
                                .idCliente(c.getCliente().getIdCliente())
                                .nombre(c.getCliente().getNombre())
                                .email(c.getCliente().getEmail())
                                .estado(c.getCliente()
                                .isEstado())
                                .build())
                            .build())
                            .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado la compra con id " + id));
    }

    @Override
    public CompraResponseDto crearCompra(CompraRequestDto dto) {
        
        // Obtener Cliente
        Cliente cliente = clienteRepository.findById(dto.getIdCliente())
                            .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado el paciente con id " + dto.getIdCliente()));


        // Validar Productos
        List<Long> idsProductos = dto.getProductos()
                                .stream()
                                .map(ProductoCompraRequestDto::getIdProducto)
                                .toList();

        // Ver si Existen todos los productos
        List<Producto> productos = productoRepository.findAllById(idsProductos);

        // Validar que se hayan encontrado todos los productos
        if(productos.size() != idsProductos.size()){

            List<Long> encontrados = productos.stream()
                                          .map(Producto::getIdProducto)
                                          .toList();

            List<Long> noEncontrados = idsProductos.stream()
                                            .filter(id -> !encontrados.contains(id))
                                            .toList();
            throw new ResourceNotFoundException("Productos no encontrados con ids: " + noEncontrados);
        }

        // Verificar stock de todos antes de modificar cualquiera
        Map<Long, Producto> mapaProductos = productos.stream()
                                                    .collect(Collectors.toMap(p -> p.getIdProducto(), p -> p));

        for (ProductoCompraRequestDto item : dto.getProductos()) {
            Producto p = mapaProductos.get(item.getIdProducto());
            if (p.getStock() < item.getCantidad()) {
                throw new StockInsuficienteException("Stock insuficiente para producto: " + p.getNombre() + ". Stock disponible: " + p.getStock());
            }
        }

        // Crear Compra
        Compra compra = Compra.builder()
                        .fechaCompra(LocalDateTime.now())
                        .cliente(cliente)
                        .build();

        compraRepository.save(compra);

        List<DetalleCompra> detalles = new ArrayList<>();
        for (ProductoCompraRequestDto item : dto.getProductos()) {
            Producto p = mapaProductos.get(item.getIdProducto());

            detalles.add(DetalleCompra.builder()
                                .cantidadProductoDetalleCompra(item.getCantidad())
                                .precioUnitarioDetalleCompra(p.getPrecio())
                                .producto(p)
                                .compra(compra)
                                .build());

            // Descontar stock
            p.setStock(p.getStock() - item.getCantidad());
        }

        detalleCompraRepository.saveAll(detalles);
        productoRepository.saveAll(mapaProductos.values());

        // FASE 4: Historial inicial
        HistorialEstados estadoInicial = HistorialEstados.builder()
                                                    .compra(compra)
                                                    .fechaInicioEstado(LocalDateTime.now())
                                                    .fechaTerminoEstado(null)
                                                    .estadoCompra(estadoCompraRepository.findByNombreEstado("PREPARACION")
                                                        .orElseThrow(() -> new ResourceNotFoundException("Estado PREPARACION no encontrado")))
                                                    .build();

        historialEstadosRepository.save(estadoInicial);

        return CompraResponseDto.builder()
                            .idCompra(compra.getIdCompra())
                            .fechaCompra(compra.getFechaCompra())
                            .cliente(ClienteResponseDto
                                .builder()
                                .idCliente(cliente.getIdCliente())
                                .nombre(cliente.getNombre())
                                .email(cliente.getEmail())
                                .estado(cliente.isEstado())
                                .build())
                            .build();
    }
}
