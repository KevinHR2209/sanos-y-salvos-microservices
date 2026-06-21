package com.sanosysalvos.mscoincidencias.controller;

import com.sanosysalvos.mscoincidencias.dto.CoincidenciaDTO;
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
    public ResponseEntity<List<CoincidenciaDTO>> listarTodas() {
        return ResponseEntity.ok(coincidenciaService.listarTodas());
    }

    @GetMapping("/pendientes")
    public ResponseEntity<List<CoincidenciaDTO>> listarPendientes() {
        return ResponseEntity.ok(coincidenciaService.listarPendientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoincidenciaDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(coincidenciaService.obtenerPorId(id));
    }

    @PostMapping("/buscar")
    public ResponseEntity<List<CoincidenciaDTO>> ejecutarBusqueda() {
        return ResponseEntity.ok(coincidenciaService.ejecutarBusquedaAutomatica());
    }

    @PatchMapping("/{id}/confirmar")
    public ResponseEntity<CoincidenciaDTO> confirmar(@PathVariable Long id) {
        return ResponseEntity.ok(coincidenciaService.confirmar(id));
    }

    @PatchMapping("/{id}/descartar")
    public ResponseEntity<CoincidenciaDTO> descartar(@PathVariable Long id) {
        return ResponseEntity.ok(coincidenciaService.descartar(id));
    }
}
