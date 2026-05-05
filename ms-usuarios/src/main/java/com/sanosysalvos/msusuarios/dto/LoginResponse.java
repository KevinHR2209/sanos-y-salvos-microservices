// LoginResponse.java
package com.sanosysalvos.msusuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Set;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String tipo = "Bearer";
    private Long usuarioId;
    private String email;
    private String nombre;
    private Set<String> roles;
}