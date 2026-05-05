package com.sanosysalvos.msreportes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MascotaDTO {
    private Long id;
    private String nombre;
    private String especie;
    private String raza;
    private String color;
    private String tamanio;
    private String sexo;
    private String descripcion;
    private Boolean tieneMicrochip;
    private String numeroMicrochip;
    private String fotoUrl;
    private Long usuarioId;
}