// ReporteService.java
package com.sanosysalvos.msreportes.service;

import com.sanosysalvos.msreportes.model.Reporte;
import java.util.List;

public interface ReporteService {
    List<Reporte> listarTodos();
    Reporte obtenerPorId(Long id);
    Reporte crear(Reporte reporte);
    Reporte actualizar(Long id, Reporte reporte);
    Reporte cambiarEstado(Long id, String estado);
    void eliminar(Long id);
    List<Reporte> obtenerPorTipo(String tipo);
    List<Reporte> obtenerPorComuna(String comuna);
}