// ReporteController.java
package com.sanosysalvos.msreportes.controller;

import com.sanosysalvos.msreportes.model.Reporte;
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
    public ResponseEntity<List<Reporte>> listarTodos() {
        return ResponseEntity.ok(reporteService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reporte> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reporteService.obtenerPorId(id));
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Reporte>> obtenerPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(reporteService.obtenerPorTipo(tipo.toUpperCase()));
    }

    @GetMapping("/perdidos")
    public ResponseEntity<List<Reporte>> listarPerdidos() {
        return ResponseEntity.ok(reporteService.obtenerPorTipo("PERDIDO"));
    }

    @GetMapping("/encontrados")
    public ResponseEntity<List<Reporte>> listarEncontrados() {
        return ResponseEntity.ok(reporteService.obtenerPorTipo("ENCONTRADO"));
    }

    @GetMapping("/comuna/{comuna}")
    public ResponseEntity<List<Reporte>> obtenerPorComuna(@PathVariable String comuna) {
        return ResponseEntity.ok(reporteService.obtenerPorComuna(comuna));
    }

    @PostMapping
    public ResponseEntity<Reporte> crear(@Valid @RequestBody Reporte reporte) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reporteService.crear(reporte));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reporte> actualizar(@PathVariable Long id,
                                              @Valid @RequestBody Reporte reporte) {
        return ResponseEntity.ok(reporteService.actualizar(id, reporte));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Reporte> cambiarEstado(@PathVariable Long id,
                                                 @RequestParam String estado) {
        return ResponseEntity.ok(reporteService.cambiarEstado(id, estado.toUpperCase()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        reporteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}