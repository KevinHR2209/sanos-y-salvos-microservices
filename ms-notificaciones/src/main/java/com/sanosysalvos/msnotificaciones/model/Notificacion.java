package com.sanosysalvos.msnotificaciones.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
@Data
@NoArgsConstructor
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(nullable = false)
    private String tipo; // COINCIDENCIA_ENCONTRADA, REPORTE_ACTUALIZADO, MASCOTA_RECUPERADA

    @Column(nullable = false)
    private String mensaje;

    @Column(nullable = false)
    private Boolean leida = false;

    @Column(name = "reporte_id")
    private Long reporteId;

    @Column(name = "coincidencia_id")
    private Long coincidenciaId;

    @Column(name = "creado_en")
    private LocalDateTime creadoEn;

    @Column(name = "leida_en")
    private LocalDateTime leidaEn;

    @PrePersist
    protected void onCreate() {
        creadoEn = LocalDateTime.now();
    }
}
