package com.sanosysalvos.msreportes.service;

import com.sanosysalvos.msreportes.dto.ReporteDTO;
import com.sanosysalvos.msreportes.dto.ReporteRequestDTO;

import java.util.List;

public interface ReporteService {

    List<ReporteDTO> listarTodos();

    ReporteDTO obtenerPorId(Long id);

    List<ReporteDTO> obtenerPorTipo(String tipo);

    List<ReporteDTO> obtenerPorComuna(String comuna);

    ReporteDTO crear(ReporteRequestDTO request);

    ReporteDTO actualizar(Long id, ReporteRequestDTO request);

    ReporteDTO cambiarEstado(Long id, String estado);

    void eliminar(Long id);
}
