package com.sanosysalvos.mscoincidencias.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de respuesta para Coincidencia.
 * Nunca se expone la entidad JPA directamente en los controllers.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoincidenciaDTO {
    private Long id;
    private Long reportePerdidoId;
    private Long reporteEncontradoId;
    private BigDecimal puntajeSimilitud;
    private String estado; // PENDIENTE, CONFIRMADA, DESCARTADA
    private String criteriosMatch;
    private LocalDateTime creadoEn;
    private LocalDateTime confirmadoEn;
}
