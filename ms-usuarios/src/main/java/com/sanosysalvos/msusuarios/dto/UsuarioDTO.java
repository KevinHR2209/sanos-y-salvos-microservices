// UsuarioDTO.java
package com.sanosysalvos.msusuarios.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String comuna;
    private String region;
    private Boolean activo;
    private Set<String> roles;
    private LocalDateTime creadoEn;
}