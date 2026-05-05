// CoincidenciaDTO.java
package com.sanosysalvos.mscoincidencias.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CoincidenciaDTO {
    private Long id;
    private Long reportePerdidoId;
    private Long reporteEncontradoId;
    private BigDecimal puntajeSimilitud;
    private String estado;
    private String criteriosMatch;
    private LocalDateTime creadoEn;
}