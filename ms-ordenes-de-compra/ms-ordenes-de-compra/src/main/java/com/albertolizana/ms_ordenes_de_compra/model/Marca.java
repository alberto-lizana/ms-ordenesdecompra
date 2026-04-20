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
@Table(name="marca")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor        
@Getter
@Setter
@Builder
public class Marca {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long idMarca;

    @Column(name="nombre", nullable=false, unique=true)
    private String nombre;

}
