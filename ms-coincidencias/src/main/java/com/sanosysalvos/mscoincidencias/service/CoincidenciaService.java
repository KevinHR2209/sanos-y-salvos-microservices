// CoincidenciaService.java
package com.sanosysalvos.mscoincidencias.service;

import com.sanosysalvos.mscoincidencias.model.Coincidencia;
import java.util.List;

public interface CoincidenciaService {
    List<Coincidencia> listarTodas();
    List<Coincidencia> listarPendientes();
    Coincidencia obtenerPorId(Long id);
    List<Coincidencia> ejecutarBusquedaAutomatica();
    Coincidencia confirmar(Long id);
    Coincidencia descartar(Long id);
}