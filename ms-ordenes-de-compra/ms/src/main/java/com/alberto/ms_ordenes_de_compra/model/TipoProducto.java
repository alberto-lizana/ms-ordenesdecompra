package com.albertolizana.ms_ordenes_de_compra.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="tipo_producto")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor        
@Getter
@Setter
@Builder

public class TipoProducto {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long idTipoProducto;

    @Column(name="nombre", unique=true, nullable=false, length=125)
    private String nombre;
    
}
