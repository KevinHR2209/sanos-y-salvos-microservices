// MascotaService.java
package com.sanosysalvos.msreportes.service;

import com.sanosysalvos.msreportes.dto.MascotaDTO;
import com.sanosysalvos.msreportes.dto.MascotaRequestDTO;
import java.util.List;

public interface MascotaService {
    List<MascotaDTO> listarTodas();
    MascotaDTO obtenerPorId(Long id);
    MascotaDTO crear(MascotaRequestDTO mascotaRequest);
    MascotaDTO actualizar(Long id, MascotaRequestDTO mascotaRequest);
    void eliminar(Long id);
    List<MascotaDTO> obtenerPorUsuario(Long usuarioId);
}
