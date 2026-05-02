package com.albertolizana.ms_ordenes_de_compra.service.ServiceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.albertolizana.ms_ordenes_de_compra.dto.DetalleCompraResponseDto;
import com.albertolizana.ms_ordenes_de_compra.dto.MarcaResponseDto;
import com.albertolizana.ms_ordenes_de_compra.dto.ProductoResponseDto;
import com.albertolizana.ms_ordenes_de_compra.dto.TipoProductoResponseDto;
import com.albertolizana.ms_ordenes_de_compra.exception.ResourceNotFoundException;
import com.albertolizana.ms_ordenes_de_compra.repository.CompraRepository;
import com.albertolizana.ms_ordenes_de_compra.repository.DetalleCompraRepository;
import com.albertolizana.ms_ordenes_de_compra.service.DetalleCompraService;

@Service
@Transactional
public class DetalleCompraServiceImpl implements DetalleCompraService {

    private final DetalleCompraRepository detalleCompraRepository;
    private final CompraRepository compraRepository;

    public DetalleCompraServiceImpl(DetalleCompraRepository detalleCompraRepository, CompraRepository compraRepository) {
        this.detalleCompraRepository = detalleCompraRepository;
        this.compraRepository = compraRepository;
    }

    @Override
    public List<DetalleCompraResponseDto> getDetallesPorCompra(Long idCompra) {

        if (!compraRepository.existsById(idCompra)) {
            throw new ResourceNotFoundException("No se ha encontrado la compra con id " + idCompra);
        }

        return detalleCompraRepository.findByCompraWithRelations(idCompra)
                                .stream()
                                .map(dc -> DetalleCompraResponseDto.builder()
                                        .idDetalleCompra(dc.getIdDetalleCompra())
                                        .cantidad(dc.getCantidadProductoDetalleCompra())
                                        .precioUnitario(dc.getPrecioUnitarioDetalleCompra())
                                        .subtotal(dc.getCantidadProductoDetalleCompra() * dc.getPrecioUnitarioDetalleCompra())

                                        .producto(ProductoResponseDto.builder()
                                                .idProducto(dc.getProducto().getIdProducto())
                                                .nombre(dc.getProducto().getNombre())
                                                .precio(dc.getProducto().getPrecio())
                                                .stock(dc.getProducto().getStock())

                                                .tipoProducto(TipoProductoResponseDto.builder()
                                                        .idTipoProducto(dc.getProducto().getTipoProducto().getIdTipoProducto())
                                                        .nombre(dc.getProducto().getTipoProducto().getNombre())
                                                        .build())

                                                .marca(MarcaResponseDto.builder()
                                                        .idMarca(dc.getProducto().getMarca().getIdMarca())
                                                        .nombre(dc.getProducto().getMarca().getNombre())
                                                        .build())
                                                .build())
                                        .build())
                                .toList();
    }

    @Override
    public DetalleCompraResponseDto getDetalle(Long id) {
        return detalleCompraRepository.findByIdWithRelations(id)
                                .map(dc -> DetalleCompraResponseDto
                                    .builder()
                                    .idDetalleCompra(dc.getIdDetalleCompra())
                                    .cantidad(dc.getCantidadProductoDetalleCompra())
                                    .precioUnitario(dc.getPrecioUnitarioDetalleCompra())
                                    .subtotal(dc.getCantidadProductoDetalleCompra() * dc.getPrecioUnitarioDetalleCompra())

                                        .producto(ProductoResponseDto
                                            .builder()
                                            .idProducto(dc.getProducto().getIdProducto())
                                            .nombre(dc.getProducto().getNombre())
                                            .precio(dc.getProducto().getPrecio())
                                            .stock(dc.getProducto().getStock())

                                                .tipoProducto(TipoProductoResponseDto
                                                    .builder()
                                                    .idTipoProducto(dc.getProducto().getTipoProducto().getIdTipoProducto())
                                                    .nombre(dc.getProducto().getTipoProducto().getNombre())
                                                .build())

                                                .marca(MarcaResponseDto
                                                    .builder()
                                                    .idMarca(dc.getProducto().getMarca().getIdMarca())
                                                    .nombre(dc.getProducto().getMarca().getNombre())
                                                .build())
                                            .build())
                                    .build())
                                .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado el detalle con id " + id));
    }
}