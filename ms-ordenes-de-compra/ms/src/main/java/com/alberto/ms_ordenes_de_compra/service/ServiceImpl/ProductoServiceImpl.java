package com.albertolizana.ms_ordenes_de_compra.service.ServiceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.albertolizana.ms_ordenes_de_compra.dto.MarcaResponseDto;
import com.albertolizana.ms_ordenes_de_compra.dto.ProductoRequestDto;
import com.albertolizana.ms_ordenes_de_compra.dto.ProductoResponseDto;
import com.albertolizana.ms_ordenes_de_compra.dto.TipoProductoResponseDto;
import com.albertolizana.ms_ordenes_de_compra.exception.ResourceNotFoundException;
import com.albertolizana.ms_ordenes_de_compra.model.Marca;
import com.albertolizana.ms_ordenes_de_compra.model.Producto;
import com.albertolizana.ms_ordenes_de_compra.model.TipoProducto;
import com.albertolizana.ms_ordenes_de_compra.repository.MarcaRepository;
import com.albertolizana.ms_ordenes_de_compra.repository.ProductoRepository;
import com.albertolizana.ms_ordenes_de_compra.repository.TipoProductoRepository;
import com.albertolizana.ms_ordenes_de_compra.service.ProductoService;

@Service
@Transactional
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final TipoProductoRepository tipoProductoRepository;
    private final MarcaRepository marcaRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository, TipoProductoRepository tipoProductoRepository, MarcaRepository marcaRepository)
    {
        this.productoRepository = productoRepository;
        this.tipoProductoRepository = tipoProductoRepository;
        this.marcaRepository = marcaRepository;
    }

    @Override
    public List<ProductoResponseDto> getProductos() {
        return productoRepository.findAllWithRelations()
                            .stream()
                            .map(p -> ProductoResponseDto
                                .builder()
                                .idProducto(p.getIdProducto())
                                .nombre(p.getNombre())
                                .stock(p.getStock())
                                .precio(p.getPrecio())
                                
                                .tipoProducto(TipoProductoResponseDto
                                    .builder()
                                    .nombre(p.getTipoProducto().getNombre())
                                    .build())
                                
                                .marca(MarcaResponseDto
                                    .builder()
                                    .nombre(p.getMarca().getNombre())
                                .build())
                            .build())
                            .toList();
    }

    @Override
    public ProductoResponseDto getProducto(Long id) {
        return productoRepository.findWithRelations(id)
                            .map(p -> ProductoResponseDto
                                .builder()
                                .idProducto(p.getIdProducto())
                                .nombre(p.getNombre())
                                .stock(p.getStock())
                                .precio(p.getPrecio())
                                
                                .tipoProducto(TipoProductoResponseDto
                                    .builder()
                                    .nombre(p.getTipoProducto().getNombre())
                                    .build())
                                
                                .marca(MarcaResponseDto
                                    .builder()
                                    .nombre(p.getMarca().getNombre())
                                .build())
                            .build())
                            .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado el producto con id " + id));
    }

    @Override
    public ProductoResponseDto modificarProducto(ProductoRequestDto dto) {
        Producto p = productoRepository.findById(dto.getIdProducto())
                                    .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado el producto con id " + dto.getIdProducto()));
        if (dto.getIdTipoProducto() != null){
            TipoProducto tp = tipoProductoRepository.findById(dto.getIdTipoProducto())
                                            .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado el tipo producto con id " + dto.getIdTipoProducto()));
            p.setTipoProducto(tp);
        }

        if (dto.getIdMarca() != null){
            Marca m = marcaRepository.findById(dto.getIdMarca())
                                        .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado la marca con id " + dto.getIdMarca()));
            p.setMarca(m);
        }      
        
        if (dto.getNombre() != null) {
            p.setNombre(dto.getNombre().toUpperCase().trim());
        }

        if (dto.getStock() != null) {
            p.setStock(dto.getStock());
        }

        if (dto.getPrecio() != null) {
            p.setPrecio(dto.getPrecio());
        }        

        productoRepository.save(p);
        
        return ProductoResponseDto.builder()
                            .idProducto(p.getIdProducto())
                            .nombre(p.getNombre())
                            .stock(p.getStock())
                            .precio(p.getPrecio())
                            
                            .tipoProducto(TipoProductoResponseDto.builder()
                                .idTipoProducto(p.getTipoProducto().getIdTipoProducto())
                                .nombre(p.getTipoProducto().getNombre())
                            .build())

                            .marca(MarcaResponseDto.builder()
                                .idMarca(p.getMarca().getIdMarca())
                                .nombre(p.getMarca().getNombre())
                            .build())
                        .build();
    }
}
