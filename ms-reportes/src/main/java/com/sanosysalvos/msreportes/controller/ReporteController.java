package com.sanosysalvos.msreportes.controller;

import com.sanosysalvos.msreportes.dto.ReporteDTO;
import com.sanosysalvos.msreportes.dto.ReporteRequestDTO;
import com.sanosysalvos.msreportes.service.ReporteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteService reporteService;

    @GetMapping
    public ResponseEntity<List<ReporteDTO>> listarTodos() {
        return ResponseEntity.ok(reporteService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReporteDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reporteService.obtenerPorId(id));
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<ReporteDTO>> obtenerPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(reporteService.obtenerPorTipo(tipo));
    }

    @GetMapping("/perdidos")
    public ResponseEntity<List<ReporteDTO>> listarPerdidos() {
        return ResponseEntity.ok(reporteService.obtenerPorTipo("PERDIDO"));
    }

    @GetMapping("/encontrados")
    public ResponseEntity<List<ReporteDTO>> listarEncontrados() {
        return ResponseEntity.ok(reporteService.obtenerPorTipo("ENCONTRADO"));
    }

    @GetMapping("/comuna/{comuna}")
    public ResponseEntity<List<ReporteDTO>> obtenerPorComuna(@PathVariable String comuna) {
        return ResponseEntity.ok(reporteService.obtenerPorComuna(comuna));
    }

    @PostMapping
    public ResponseEntity<ReporteDTO> crear(@Valid @RequestBody ReporteRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reporteService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReporteDTO> actualizar(@PathVariable Long id,
                                                 @Valid @RequestBody ReporteRequestDTO request) {
        return ResponseEntity.ok(reporteService.actualizar(id, request));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<ReporteDTO> cambiarEstado(@PathVariable Long id,
                                                    @RequestParam String estado) {
        return ResponseEntity.ok(reporteService.cambiarEstado(id, estado.toUpperCase()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        reporteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
