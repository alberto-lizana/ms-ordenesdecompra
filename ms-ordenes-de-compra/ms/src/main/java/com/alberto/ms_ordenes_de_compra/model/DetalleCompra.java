package com.albertolizana.ms_ordenes_de_compra.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="detalle_compra")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor        
@Getter
@Setter
@Builder
public class DetalleCompra {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long idDetalleCompra;

    @Column(name="cantidad", nullable=false)
    private Integer cantidadProductoDetalleCompra;

    @Column(name="precio_unitario", nullable=false)
    private Integer precioUnitarioDetalleCompra;

    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name="idCompra", nullable=false)
    private Compra compra;

    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name="idProducto", nullable=false)
    private Producto producto;

}
