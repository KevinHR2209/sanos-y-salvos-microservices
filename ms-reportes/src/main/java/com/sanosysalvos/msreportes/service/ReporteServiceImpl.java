package com.sanosysalvos.msreportes.service;

import com.sanosysalvos.msreportes.dto.ReporteDTO;
import com.sanosysalvos.msreportes.dto.ReporteRequestDTO;
import com.sanosysalvos.msreportes.exception.ResourceNotFoundException;
import com.sanosysalvos.msreportes.model.Mascota;
import com.sanosysalvos.msreportes.model.Reporte;
import com.sanosysalvos.msreportes.repository.MascotaRepository;
import com.sanosysalvos.msreportes.repository.ReporteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteService {

    private final ReporteRepository reporteRepository;
    private final MascotaRepository mascotaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ReporteDTO> listarTodos() {
        return reporteRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ReporteDTO obtenerPorId(Long id) {
        return toDTO(findOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReporteDTO> obtenerPorTipo(String tipo) {
        return reporteRepository.findByTipo(tipo).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReporteDTO> obtenerPorComuna(String comuna) {
        return reporteRepository.findByComunaIgnoreCase(comuna).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ReporteDTO crear(ReporteRequestDTO request) {
        Reporte reporte = fromRequest(request, new Reporte());
        return toDTO(reporteRepository.save(reporte));
    }

    @Override
    @Transactional
    public ReporteDTO actualizar(Long id, ReporteRequestDTO request) {
        Reporte reporte = findOrThrow(id);
        fromRequest(request, reporte);
        return toDTO(reporteRepository.save(reporte));
    }

    @Override
    @Transactional
    public ReporteDTO cambiarEstado(Long id, String estado) {
        Reporte reporte = findOrThrow(id);
        reporte.setEstado(estado);
        return toDTO(reporteRepository.save(reporte));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        findOrThrow(id);
        reporteRepository.deleteById(id);
    }

    // ---- helpers privados ----

    private Reporte findOrThrow(Long id) {
        return reporteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reporte no encontrado: " + id));
    }

    private Reporte fromRequest(ReporteRequestDTO req, Reporte reporte) {
        Mascota mascota = mascotaRepository.findById(req.getMascotaId())
                .orElseThrow(() -> new ResourceNotFoundException("Mascota no encontrada: " + req.getMascotaId()));
        reporte.setTipo(req.getTipo());
        reporte.setEstado(req.getEstado() != null ? req.getEstado() : "ACTIVO");
        reporte.setMascota(mascota);
        reporte.setUsuarioId(req.getUsuarioId());
        reporte.setLatitud(req.getLatitud());
        reporte.setLongitud(req.getLongitud());
        reporte.setDireccion(req.getDireccion());
        reporte.setComuna(req.getComuna());
        reporte.setRegion(req.getRegion());
        reporte.setFechaEvento(req.getFechaEvento());
        reporte.setDescripcion(req.getDescripcion());
        reporte.setRecompensa(req.getRecompensa());
        return reporte;
    }

    private ReporteDTO toDTO(Reporte r) {
        ReporteDTO dto = new ReporteDTO();
        dto.setId(r.getId());
        dto.setTipo(r.getTipo());
        dto.setEstado(r.getEstado());
        dto.setUsuarioId(r.getUsuarioId());
        dto.setLatitud(r.getLatitud());
        dto.setLongitud(r.getLongitud());
        dto.setDireccion(r.getDireccion());
        dto.setComuna(r.getComuna());
        dto.setRegion(r.getRegion());
        dto.setFechaEvento(r.getFechaEvento());
        dto.setDescripcion(r.getDescripcion());
        dto.setRecompensa(r.getRecompensa());
        dto.setCreadoEn(r.getCreadoEn());
        if (r.getMascota() != null) {
            dto.setMascotaId(r.getMascota().getId());
            dto.setMascotaNombre(r.getMascota().getNombre());
            dto.setMascotaEspecie(r.getMascota().getEspecie());
            dto.setMascotaRaza(r.getMascota().getRaza());
            dto.setMascotaColor(r.getMascota().getColor());
            dto.setMascotaTamanio(r.getMascota().getTamanio());
            dto.setMascotaSexo(r.getMascota().getSexo());
            dto.setMascotaFotoUrl(r.getMascota().getFotoUrl());
        }
        return dto;
    }
}
