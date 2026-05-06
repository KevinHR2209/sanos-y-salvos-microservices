package com.sanosysalvos.msgeoespacial.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteGeoDTO {
    private Long id;
    private String tipo;
    private String estado;
    private String comuna;
    private String direccion;
    private LocalDate fechaEvento;
    private BigDecimal latitud;
    private BigDecimal longitud;
    private Double distanciaKm;
}