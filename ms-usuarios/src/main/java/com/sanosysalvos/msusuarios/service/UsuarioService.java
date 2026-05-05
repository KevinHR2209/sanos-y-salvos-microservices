// UsuarioService.java
package com.sanosysalvos.msusuarios.service;

import com.sanosysalvos.msusuarios.dto.UsuarioDTO;
import com.sanosysalvos.msusuarios.model.Usuario;
import java.util.List;

public interface UsuarioService {
    List<UsuarioDTO> listarTodos();
    UsuarioDTO obtenerPorId(Long id);
    UsuarioDTO obtenerPorEmail(String email);
    Usuario actualizar(Long id, Usuario datos);
    void desactivar(Long id);
}