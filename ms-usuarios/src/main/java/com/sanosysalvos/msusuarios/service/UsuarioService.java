// UsuarioService.java
package com.sanosysalvos.msusuarios.service;

import com.sanosysalvos.msusuarios.dto.UsuarioDTO;
import java.util.List;

public interface UsuarioService {
    List<UsuarioDTO> listarTodos();
    UsuarioDTO obtenerPorId(Long id);
    UsuarioDTO obtenerPorEmail(String email);
    UsuarioDTO actualizar(Long id, UsuarioDTO datos);
    void desactivar(Long id);
}
