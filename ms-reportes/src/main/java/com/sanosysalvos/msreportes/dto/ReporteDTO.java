package com.sanosysalvos.msreportes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteDTO {
    private Long id;
    private String tipo;
    private String estado;
    private Long usuarioId;
    private Long mascotaId;
    private BigDecimal latitud;
    private BigDecimal longitud;
    private String direccion;
    private String region;
    private String comuna;
    private LocalDate fechaEvento;
    private String descripcion;
    private BigDecimal recompensa;
    // Datos resumidos de la mascota para conveniencia del cliente
    private String mascotaNombre;
    private String mascotaEspecie;
    private String mascotaRaza;
    private String mascotaColor;
    private String mascotaTamanio;
    private String mascotaSexo;
    private String mascotaFotoUrl;
    private LocalDateTime creadoEn;
}
