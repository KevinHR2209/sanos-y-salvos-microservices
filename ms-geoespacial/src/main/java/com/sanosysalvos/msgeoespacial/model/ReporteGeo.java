package com.sanosysalvos.msgeoespacial.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reportes")
@Data
@NoArgsConstructor
public class ReporteGeo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private String estado;

    @Column(name = "usuario_id")
    private Long usuarioId;

    private BigDecimal latitud;
    private BigDecimal longitud;
    private String direccion;
    private String comuna;
    private String region;

    @Column(name = "fecha_evento")
    private LocalDate fechaEvento;

    private String descripcion;

    @Column(name = "ubicacion", columnDefinition = "geometry(Point,4326)")
    private Point ubicacion;

    @Column(name = "creado_en")
    private LocalDateTime creadoEn;
}