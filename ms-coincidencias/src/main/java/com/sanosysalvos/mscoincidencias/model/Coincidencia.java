package com.sanosysalvos.mscoincidencias.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "coincidencias")
@Data
@NoArgsConstructor
public class Coincidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reporte_perdido_id", nullable = false)
    private Long reportePerdidoId;

    @Column(name = "reporte_encontrado_id", nullable = false)
    private Long reporteEncontradoId;

    @Column(name = "puntaje_similitud", precision = 5, scale = 2)
    private BigDecimal puntajeSimilitud;

    @Column(nullable = false)
    private String estado = "PENDIENTE"; // PENDIENTE, CONFIRMADA, DESCARTADA

    @Column(name = "criterios_match", columnDefinition = "TEXT")
    private String criteriosMatch; // JSON con detalle

    @Column(name = "creado_en")
    private LocalDateTime creadoEn;

    @Column(name = "confirmado_en")
    private LocalDateTime confirmadoEn;

    @PrePersist
    protected void onCreate() {
        creadoEn = LocalDateTime.now();
    }
}