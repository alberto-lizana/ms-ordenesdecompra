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
@Table(name="cliente")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor        
@Getter
@Setter
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long idCliente;

    @Column(name="nombre", nullable=false, length=75)
    private String nombre;

    @Column(name="email", unique=true, nullable=false, length=150)
    private String email;

    @Column(name="estado", nullable=false)
    private boolean estado;

}