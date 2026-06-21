package com.sanosysalvos.mscoincidencias.service;

import com.sanosysalvos.mscoincidencias.dto.CoincidenciaDTO;
import java.util.List;

public interface CoincidenciaService {
    List<CoincidenciaDTO> listarTodas();
    List<CoincidenciaDTO> listarPendientes();
    CoincidenciaDTO obtenerPorId(Long id);
    List<CoincidenciaDTO> ejecutarBusquedaAutomatica();
    CoincidenciaDTO confirmar(Long id);
    CoincidenciaDTO descartar(Long id);
}
