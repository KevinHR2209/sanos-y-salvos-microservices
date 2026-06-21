package com.sanosysalvos.msintegracion.controller;

import com.sanosysalvos.msintegracion.client.CoincidenciasClient;
import com.sanosysalvos.msintegracion.client.ReportesClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/integracion")
@RequiredArgsConstructor
public class IntegracionController {

    private final ReportesClient reportesClient;
    private final CoincidenciasClient coincidenciasClient;

    /**
     * Obtiene reportes de ms-reportes con Circuit Breaker.
     * Si ms-reportes falla, retorna lista vacia como fallback.
     */
    @GetMapping("/reportes")
    @CircuitBreaker(name = "ms-reportes", fallbackMethod = "fallbackReportes")
    public ResponseEntity<List<Map<String, Object>>> obtenerReportes() {
        return ResponseEntity.ok(reportesClient.listarReportes());
    }

    @GetMapping("/reportes/perdidos")
    @CircuitBreaker(name = "ms-reportes", fallbackMethod = "fallbackReportes")
    public ResponseEntity<List<Map<String, Object>>> obtenerPerdidos() {
        return ResponseEntity.ok(reportesClient.listarPerdidos());
    }

    /**
     * Dispara el motor de coincidencias con Circuit Breaker.
     */
    @PostMapping("/coincidencias/buscar")
    @CircuitBreaker(name = "ms-coincidencias", fallbackMethod = "fallbackCoincidencias")
    public ResponseEntity<List<Map<String, Object>>> ejecutarBusqueda() {
        return ResponseEntity.ok(coincidenciasClient.ejecutarBusqueda());
    }

    @GetMapping("/coincidencias/pendientes")
    @CircuitBreaker(name = "ms-coincidencias", fallbackMethod = "fallbackCoincidencias")
    public ResponseEntity<List<Map<String, Object>>> obtenerPendientes() {
        return ResponseEntity.ok(coincidenciasClient.listarPendientes());
    }

    // ---- Fallbacks del Circuit Breaker ----

    public ResponseEntity<List<Map<String, Object>>> fallbackReportes(Exception ex) {
        log.warn("Circuit Breaker activado para ms-reportes: {}", ex.getMessage());
        return ResponseEntity.ok(Collections.emptyList());
    }

    public ResponseEntity<List<Map<String, Object>>> fallbackCoincidencias(Exception ex) {
        log.warn("Circuit Breaker activado para ms-coincidencias: {}", ex.getMessage());
        return ResponseEntity.ok(Collections.emptyList());
    }
}
