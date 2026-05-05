package com.sanosysalvos.msreportes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "reportes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El tipo es obligatorio")
    @Column(nullable = false)
    private String tipo; // PERDIDO, ENCONTRADO

    @Column(nullable = false)
    private String estado = "ACTIVO";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mascota_id")
    private Mascota mascota;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    private BigDecimal latitud;
    private BigDecimal longitud;
    private String direccion;
    private String comuna;
    private String region;

    @NotNull(message = "La fecha del evento es obligatoria")
    @Column(name = "fecha_evento", nullable = false)
    private LocalDate fechaEvento;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    private BigDecimal recompensa;

    @OneToMany(mappedBy = "reporte", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ReporteImagen> imagenes;

    @Column(name = "creado_en")
    private LocalDateTime creadoEn;

    @Column(name = "actualizado_en")
    private LocalDateTime actualizadoEn;

    @PrePersist
    protected void onCreate() {
        creadoEn = LocalDateTime.now();
        actualizadoEn = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        actualizadoEn = LocalDateTime.now();
    }
}