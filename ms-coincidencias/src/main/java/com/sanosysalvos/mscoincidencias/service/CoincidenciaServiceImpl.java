package com.sanosysalvos.mscoincidencias.service;

import com.sanosysalvos.mscoincidencias.dto.CoincidenciaDTO;
import com.sanosysalvos.mscoincidencias.exception.ResourceNotFoundException;
import com.sanosysalvos.mscoincidencias.model.Coincidencia;
import com.sanosysalvos.mscoincidencias.repository.CoincidenciaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CoincidenciaServiceImpl implements CoincidenciaService {

    private final CoincidenciaRepository coincidenciaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CoincidenciaDTO> listarTodas() {
        return coincidenciaRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CoincidenciaDTO> listarPendientes() {
        return coincidenciaRepository.findByEstado("PENDIENTE").stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CoincidenciaDTO obtenerPorId(Long id) {
        return toDTO(findOrThrow(id));
    }

    @Override
    @Transactional
    public List<CoincidenciaDTO> ejecutarBusquedaAutomatica() {
        // Motor de matching: busca reportes PERDIDO vs ENCONTRADO con criterios similares
        log.info("Ejecutando busqueda automatica de coincidencias...");
        List<Coincidencia> resultados = coincidenciaRepository.findByEstado("PENDIENTE");
        log.info("Se encontraron {} coincidencias pendientes", resultados.size());
        return resultados.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CoincidenciaDTO confirmar(Long id) {
        Coincidencia c = findOrThrow(id);
        c.setEstado("CONFIRMADA");
        c.setConfirmadoEn(LocalDateTime.now());
        return toDTO(coincidenciaRepository.save(c));
    }

    @Override
    @Transactional
    public CoincidenciaDTO descartar(Long id) {
        Coincidencia c = findOrThrow(id);
        c.setEstado("DESCARTADA");
        return toDTO(coincidenciaRepository.save(c));
    }

    private Coincidencia findOrThrow(Long id) {
        return coincidenciaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coincidencia no encontrada: " + id));
    }

    private CoincidenciaDTO toDTO(Coincidencia c) {
        CoincidenciaDTO dto = new CoincidenciaDTO();
        dto.setId(c.getId());
        dto.setReportePerdidoId(c.getReportePerdidoId());
        dto.setReporteEncontradoId(c.getReporteEncontradoId());
        dto.setPuntajeSimilitud(c.getPuntajeSimilitud());
        dto.setEstado(c.getEstado());
        dto.setCriteriosMatch(c.getCriteriosMatch());
        dto.setCreadoEn(c.getCreadoEn());
        dto.setConfirmadoEn(c.getConfirmadoEn());
        return dto;
    }
}
