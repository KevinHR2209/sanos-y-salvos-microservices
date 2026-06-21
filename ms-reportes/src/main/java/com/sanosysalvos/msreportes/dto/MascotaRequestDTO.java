package com.sanosysalvos.msreportes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO utilizado para recibir datos al crear o actualizar una Mascota.
 * Evita exponer la entidad directamente en la capa de presentación.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MascotaRequestDTO {

    private String nombre;

    @NotBlank(message = "La especie es obligatoria")
    private String especie; // PERRO, GATO, OTRO

    private String raza;
    private String color;
    private String tamanio;
    private String sexo;
    private String descripcion;
    private Boolean tieneMicrochip;
    private String numeroMicrochip;
    private String fotoUrl;

    @NotNull(message = "El id del usuario es obligatorio")
    private Long usuarioId;
}
