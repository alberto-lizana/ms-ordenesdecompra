package com.albertolizana.ms_ordenes_de_compra.model;

import java.time.LocalDateTime;

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
@Table(name="historial_estados")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor        
@Getter
@Setter
@Builder
public class HistorialEstados {
   
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long idHistorialEstados;

    @Column(name="fecha_inicio", nullable=false)
    private LocalDateTime fechaInicioEstado;

    @Column(name="fecha_termino", nullable=true)
    private LocalDateTime fechaTerminoEstado;

    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name="idEstadoCompra", nullable=false)
    private EstadoCompra estadoCompra;
    
    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name="idCompra", nullable=false)
    private Compra compra;

}
