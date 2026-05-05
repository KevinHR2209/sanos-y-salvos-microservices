package com.sanosysalvos.msreportes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteDTO {
    private Long id;
    private String tipo;
    private String estado;
    private Long usuarioId;
    private BigDecimal latitud;
    private BigDecimal longitud;
    private String comuna;
    private LocalDate fechaEvento;
    private String mascotaEspecie;
    private String mascotaRaza;
    private String mascotaColor;
    private String mascotaTamanio;
    private String mascotaSexo;
}