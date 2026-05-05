// CoincidenciaController.java
package com.sanosysalvos.mscoincidencias.controller;

import com.sanosysalvos.mscoincidencias.model.Coincidencia;
import com.sanosysalvos.mscoincidencias.service.CoincidenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/coincidencias")
@RequiredArgsConstructor
public class CoincidenciaController {

    private final CoincidenciaService coincidenciaService;

    @GetMapping
    public ResponseEntity<List<Coincidencia>> listarTodas() {
        return ResponseEntity.ok(coincidenciaService.listarTodas());
    }

    @GetMapping("/pendientes")
    public ResponseEntity<List<Coincidencia>> listarPendientes() {
        return ResponseEntity.ok(coincidenciaService.listarPendientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Coincidencia> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(coincidenciaService.obtenerPorId(id));
    }

    // Endpoint clave: dispara el motor de matching
    @PostMapping("/buscar")
    public ResponseEntity<List<Coincidencia>> ejecutarBusqueda() {
        return ResponseEntity.ok(coincidenciaService.ejecutarBusquedaAutomatica());
    }

    @PatchMapping("/{id}/confirmar")
    public ResponseEntity<Coincidencia> confirmar(@PathVariable Long id) {
        return ResponseEntity.ok(coincidenciaService.confirmar(id));
    }

    @PatchMapping("/{id}/descartar")
    public ResponseEntity<Coincidencia> descartar(@PathVariable Long id) {
        return ResponseEntity.ok(coincidenciaService.descartar(id));
    }
}