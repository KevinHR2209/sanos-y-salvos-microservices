// CoincidenciaServiceImpl.java
package com.sanosysalvos.mscoincidencias.service;

import com.sanosysalvos.mscoincidencias.client.ReportesClient;
import com.sanosysalvos.mscoincidencias.dto.ReporteClienteDTO;
import com.sanosysalvos.mscoincidencias.exception.ResourceNotFoundException;
import com.sanosysalvos.mscoincidencias.model.Coincidencia;
import com.sanosysalvos.mscoincidencias.repository.CoincidenciaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CoincidenciaServiceImpl implements CoincidenciaService {

    private final CoincidenciaRepository coincidenciaRepository;
    private final ReportesClient reportesClient;
    private final MotorMatchService motorMatchService;

    // Umbral mínimo para registrar una coincidencia (55%)
    private static final BigDecimal UMBRAL_MINIMO = BigDecimal.valueOf(55.0);

    @Override
    public List<Coincidencia> listarTodas() {
        return coincidenciaRepository.findAll();
    }

    @Override
    public List<Coincidencia> listarPendientes() {
        return coincidenciaRepository.findByEstado("PENDIENTE");
    }

    @Override
    public Coincidencia obtenerPorId(Long id) {
        return coincidenciaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coincidencia no encontrada: " + id));
    }

    @Override
    public List<Coincidencia> ejecutarBusquedaAutomatica() {
        log.info("Iniciando búsqueda automática de coincidencias...");

        List<ReporteClienteDTO> perdidos = reportesClient.obtenerReportesPorTipo("PERDIDO");
        List<ReporteClienteDTO> encontrados = reportesClient.obtenerReportesPorTipo("ENCONTRADO");
        List<Coincidencia> nuevas = new ArrayList<>();

        for (ReporteClienteDTO perdido : perdidos) {
            for (ReporteClienteDTO encontrado : encontrados) {

                // Evitar duplicados
                if (coincidenciaRepository.existsByReportePerdidoIdAndReporteEncontradoId(
                        perdido.getId(), encontrado.getId())) {
                    continue;
                }

                BigDecimal puntaje = motorMatchService.calcularPuntaje(perdido, encontrado);

                if (puntaje.compareTo(UMBRAL_MINIMO) >= 0) {
                    Coincidencia c = new Coincidencia();
                    c.setReportePerdidoId(perdido.getId());
                    c.setReporteEncontradoId(encontrado.getId());
                    c.setPuntajeSimilitud(puntaje);
                    c.setEstado("PENDIENTE");
                    c.setCriteriosMatch(
                            motorMatchService.criteriosAJson(perdido, encontrado)
                    );
                    nuevas.add(coincidenciaRepository.save(c));
                    log.info("Match encontrado: reporte {} con {} — puntaje: {}",
                            perdido.getId(), encontrado.getId(), puntaje);
                }
            }
        }

        log.info("Búsqueda finalizada. {} coincidencias nuevas encontradas.", nuevas.size());
        return nuevas;
    }

    @Override
    public Coincidencia confirmar(Long id) {
        Coincidencia c = obtenerPorId(id);
        c.setEstado("CONFIRMADA");
        c.setConfirmadoEn(LocalDateTime.now());
        return coincidenciaRepository.save(c);
    }

    @Override
    public Coincidencia descartar(Long id) {
        Coincidencia c = obtenerPorId(id);
        c.setEstado("DESCARTADA");
        return coincidenciaRepository.save(c);
    }
}