// ReporteServiceImpl.java
package com.sanosysalvos.msreportes.service;

import com.sanosysalvos.msreportes.exception.ResourceNotFoundException;
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
                .orElseThrow(() -> new ResourceNotFoundException("Reporte no encontrado con id: " + id));
    }

    @Override
    public Reporte crear(Reporte reporte) {
        reporte.setEstado("ACTIVO");
        return reporteRepository.save(reporte);
    }

    @Override
    public Reporte actualizar(Long id, Reporte datos) {
        Reporte existente = obtenerPorId(id);
        existente.setTipo(datos.getTipo());
        existente.setDireccion(datos.getDireccion());
        existente.setComuna(datos.getComuna());
        existente.setRegion(datos.getRegion());
        existente.setLatitud(datos.getLatitud());
        existente.setLongitud(datos.getLongitud());
        existente.setFechaEvento(datos.getFechaEvento());
        existente.setDescripcion(datos.getDescripcion());
        existente.setRecompensa(datos.getRecompensa());
        return reporteRepository.save(existente);
    }

    @Override
    public Reporte cambiarEstado(Long id, String estado) {
        Reporte existente = obtenerPorId(id);
        existente.setEstado(estado);
        return reporteRepository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        obtenerPorId(id);
        reporteRepository.deleteById(id);
    }

    @Override
    public List<Reporte> obtenerPorTipo(String tipo) {
        return reporteRepository.findByTipo(tipo);
    }

    @Override
    public List<Reporte> obtenerPorComuna(String comuna) {
        return reporteRepository.findByComuna(comuna);
    }
}