// ReporteClienteDTO.java
package com.sanosysalvos.mscoincidencias.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ReporteClienteDTO {
    private Long id;
    private String tipo;        // PERDIDO, ENCONTRADO
    private String estado;
    private Long usuarioId;
    private BigDecimal latitud;
    private BigDecimal longitud;
    private String comuna;
    private LocalDate fechaEvento;
    // Datos de la mascota anidada
    private String mascotaEspecie;
    private String mascotaRaza;
    private String mascotaColor;
    private String mascotaTamanio;
    private String mascotaSexo;
}