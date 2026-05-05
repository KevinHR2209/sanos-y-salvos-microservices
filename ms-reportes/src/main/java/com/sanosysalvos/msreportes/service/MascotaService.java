// MascotaService.java
package com.sanosysalvos.msreportes.service;

import com.sanosysalvos.msreportes.model.Mascota;
import java.util.List;

public interface MascotaService {
    List<Mascota> listarTodas();
    Mascota obtenerPorId(Long id);
    Mascota crear(Mascota mascota);
    Mascota actualizar(Long id, Mascota mascota);
    void eliminar(Long id);
    List<Mascota> obtenerPorUsuario(Long usuarioId);
}