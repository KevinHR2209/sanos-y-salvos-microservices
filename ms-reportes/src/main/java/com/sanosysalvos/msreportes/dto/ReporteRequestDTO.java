package com.sanosysalvos.msreportes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO utilizado para recibir datos al crear o actualizar un Reporte.
 * Evita exponer la entidad directamente en la capa de presentación.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteRequestDTO {

    @NotBlank(message = "El tipo es obligatorio")
    private String tipo; // PERDIDO, ENCONTRADO

    private String estado;

    @NotNull(message = "El id de la mascota es obligatorio")
    private Long mascotaId;

    @NotNull(message = "El id del usuario es obligatorio")
    private Long usuarioId;

    private BigDecimal latitud;
    private BigDecimal longitud;
    private String direccion;
    private String comuna;
    private String region;

    @NotNull(message = "La fecha del evento es obligatoria")
    private LocalDate fechaEvento;

    private String descripcion;
    private BigDecimal recompensa;
}
