package com.sanosysalvos.msreportes.service;

import com.sanosysalvos.msreportes.dto.ReporteDTO;
import com.sanosysalvos.msreportes.exception.ResourceNotFoundException;
import com.sanosysalvos.msreportes.mapper.ReporteMapper;
import com.sanosysalvos.msreportes.model.Reporte;
import com.sanosysalvos.msreportes.repository.ReporteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteService {

    private final ReporteRepository reporteRepository;

    @Override
    public List<Reporte> listarTodos() {
        return reporteRepository.findAll();
    }

    @Override
    public Reporte obtenerPorId(Long id) {
        return reporteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reporte no encontrado: " + id));
    }

    @Override
    public List<ReporteDTO> obtenerPorTipo(String tipo) {
        return reporteRepository.findByTipo(tipo)
                .stream()
                .map(ReporteMapper::toDTO)
                .toList();
    }

    @Override
    public List<Reporte> obtenerPorComuna(String comuna) {
        return reporteRepository.findByComunaIgnoreCase(comuna);
    }

    @Override
    public Reporte crear(Reporte reporte) {
        return reporteRepository.save(reporte);
    }

    @Override
    public Reporte actualizar(Long id, Reporte reporteActualizado) {
        Reporte reporte = obtenerPorId(id);

        reporte.setTipo(reporteActualizado.getTipo());
        reporte.setEstado(reporteActualizado.getEstado());
        reporte.setMascota(reporteActualizado.getMascota());
        reporte.setUsuarioId(reporteActualizado.getUsuarioId());
        reporte.setLatitud(reporteActualizado.getLatitud());
        reporte.setLongitud(reporteActualizado.getLongitud());
        reporte.setDireccion(reporteActualizado.getDireccion());
        reporte.setComuna(reporteActualizado.getComuna());
        reporte.setRegion(reporteActualizado.getRegion());
        reporte.setFechaEvento(reporteActualizado.getFechaEvento());
        reporte.setDescripcion(reporteActualizado.getDescripcion());
        reporte.setRecompensa(reporteActualizado.getRecompensa());
        reporte.setImagenes(reporteActualizado.getImagenes());

        return reporteRepository.save(reporte);
    }

    @Override
    public Reporte cambiarEstado(Long id, String estado) {
        Reporte reporte = obtenerPorId(id);
        reporte.setEstado(estado);
        return reporteRepository.save(reporte);
    }

    @Override
    public void eliminar(Long id) {
        Reporte reporte = obtenerPorId(id);
        reporteRepository.delete(reporte);
    }
}