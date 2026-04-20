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
@Table(name="estado_compra")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor        
@Getter
@Setter
@Builder

public class EstadoCompra {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="nombre_estado", nullable=false, unique=true)
    private String nombreEstado;

}
