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
@Table(name="PRODUCTO")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor        
@Getter
@Setter
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long idProducto;

    @Column(name="nombre", length=75, unique=true, nullable=false)
    private String nombre;

    @Column(name="stock", nullable=false)
    private Integer stock;

    @Column(name="precio", nullable=false)
    private Integer precio;
    
    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name="idTipoProducto", nullable=false)
    private TipoProducto tipoProducto;

    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name="idMarca", nullable=false)
    private Marca marca;

}