package com.albertolizana.ms_compra.service.ServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.albertolizana.ms_compra.dto.ClienteResponseDto;
import com.albertolizana.ms_compra.dto.CompraRequestDto;
import com.albertolizana.ms_compra.dto.CompraResponseDto;
import com.albertolizana.ms_compra.dto.ProductoCompraRequestDto;
import com.albertolizana.ms_compra.exception.ResourceNotFoundException;
import com.albertolizana.ms_compra.exception.StockInsuficienteException;
import com.albertolizana.ms_compra.model.Cliente;
import com.albertolizana.ms_compra.model.Compra;
import com.albertolizana.ms_compra.model.DetalleCompra;
import com.albertolizana.ms_compra.model.HistorialEstados;
import com.albertolizana.ms_compra.model.Producto;
import com.albertolizana.ms_compra.repository.ClienteRepository;
import com.albertolizana.ms_compra.repository.CompraRepository;
import com.albertolizana.ms_compra.repository.DetalleCompraRepository;
import com.albertolizana.ms_compra.repository.EstadoCompraRepository;
import com.albertolizana.ms_compra.repository.HistorialEstadosRepository;
import com.albertolizana.ms_compra.repository.ProductoRepository;
import com.albertolizana.ms_compra.service.CompraService;

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


        // Extraer los ids de los productos enviados por el cliente.
        List<Long> idsProductos = dto.getProductos()
                                .stream()
                                .map(ProductoCompraRequestDto::getIdProducto)
                                .toList();

        // Obtener Los productos con los ids enviados por el cliente
        List<Producto> productos = productoRepository.findAllById(idsProductos);

        // Buscar inconsistencia en ids para poder enviar un error.
        if(productos.size() != idsProductos.size()){
            
            // Extraer los IDs de la Lista de Productos llamada productos obtenidos desde la base de datos
            List<Long> encontrados = productos.stream()
                                          .map(Producto::getIdProducto)
                                          .toList();

            // Para los noEncontrados nos vamos a los ids iniciales que nos envió el Cliente lo iteramos y 
            // filtramos por Long entonces si ese long no contiene por la negación de ! 
            // el id que tenemos en nuestra lista encontrados creada anteriormente eso será guardado en la lista 
            // y al terminar de iterar enviará un error con los ids noEncontrados
            List<Long> noEncontrados = idsProductos.stream()
                                            .filter(id -> !encontrados.contains(id))
                                            .toList();
            throw new ResourceNotFoundException("Productos no encontrados con ids: " + noEncontrados);
        }

        // Transformamos la lista de productos (que si estamos aca existen todos) la iteramos
        // La transformamos en un Map pq queremos guardar productos en un map
        // luego rellenamos la key con el Produicto.getId(p -> p.getIdProducto) o (Producto::getIdProducto) que es un Long finalmente Producto con el producto completo (p -> p) o (Producto::Producto) 
        Map<Long, Producto> mapaProductos = productos.stream()
                                                .collect(Collectors.toMap(p -> p.getIdProducto(), p -> p));

    

        // Iteramos cada ProductoCompraRequestDto del dto que nos envió el cliente que tiene parámetros id y cantidad 
        // Con el dto enviado por el cliente que contiene el id del cliente y una List<ProductoCompraRequestDto> pero al hacer getProductos obtenemos la List<ProductoCompraRequestDto>
        // Luego, Producto p = mapaProductos.get(item.getIdProducto()); estamos diciendo p es el Producto que se obtuvo con el id del pedido del cliente.
        // Por lo tanto, obtenemos el productoCompraRequestDto de el dto.getProductos y lo llamamos "item" 
        // entonces "item" es lo que nuestro cliente quiere y tambien lo usamos para obtener producto (item.getIdProducto()) (con el map de productos traidos de base de dato como respuesta la id de item) 
        // finalmente hacemos un if en donde obtemos el stock real del producto con p.getStock() y decimos si este stock es menor al item.getCantidad(), es decir la cantidad que solicito el cliente damos un error.
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

        // Guardamos la compra
        compraRepository.save(compra);

        // Creamos una lista de DetalleCompra llamada detalles y es una lista instancida.
        List<DetalleCompra> detalles = new ArrayList<>();
        // Iteramos nuevamente los productos solicitados por el cliente del dto enviado por él 
        // obteniendo justamente la lista de ProductoCompraRequestDto que estamos iterando con el nombre item
        // Luego por cada id de producto obtendremos ese producto,
        for (ProductoCompraRequestDto item : dto.getProductos()) {
            Producto p = mapaProductos.get(item.getIdProducto());

            // Por cada id pedido por el cliente se agregará a la lista de DetalleCompra el detalle 
            // es decir cuantos items.getCantidad solicito de x producto el cliente 
            // como el precio que pago en el momento indicado obtenido desde la base de datos anteriormente.
            // Luego el producto completo 
            // Finalmente la compra que fue anteriormente creada
            detalles.add(DetalleCompra.builder()
                                .cantidadProductoDetalleCompra(item.getCantidad())
                                .precioUnitarioDetalleCompra(p.getPrecio())
                                .producto(p)
                                .compra(compra)
                                .build());

            // Descontar stock
            p.setStock(p.getStock() - item.getCantidad());
        }
        // Guardamos todos los detalles que se iteraron anteriormente
        detalleCompraRepository.saveAll(detalles);

        // Aquí guardamos la referencia de los objetos que obtuvimos al prinicipio como lista luego la hicimos map y ahora guardamos los values es decir Productos.
        // Guardamos el map pq en la iteración anterior p es referencia al objeto Producto almacenado dentro del Map
        // es decir el Map contiene referencias a objetos Producto en memoria. 
        // Al modificar p, se modifica el mismo objeto referenciado por el Map, porque ambos apuntan al mismo objeto en memoria.
        // En otras palabras p es una referencia al mismo objeto Producto que está dentro del Map mapaProductos
        productoRepository.saveAll(mapaProductos.values());

        // Creamos el Historial inicial dado que todos los procesos anteriores se han realziado con éxito.
        HistorialEstados estadoInicial = HistorialEstados.builder()
                                                    .compra(compra)
                                                    .fechaInicioEstado(LocalDateTime.now())
                                                    .fechaTerminoEstado(null)
                                                    .estadoCompra(estadoCompraRepository.findByNombreEstado("PREPARACION")
                                                        .orElseThrow(() -> new ResourceNotFoundException("Estado PREPARACION no encontrado")))
                                                    .build();

        historialEstadosRepository.save(estadoInicial);

        // Devolvemos la compra en su dto q es un response para informar al cliente como quedaron las cosas
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
