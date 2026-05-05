// MascotaController.java
package com.sanosysalvos.msreportes.controller;

import com.sanosysalvos.msreportes.model.Mascota;
import com.sanosysalvos.msreportes.service.MascotaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/mascotas")
@RequiredArgsConstructor
public class MascotaController {

    private final MascotaService mascotaService;

    @GetMapping
    public ResponseEntity<List<Mascota>> listarTodas() {
        return ResponseEntity.ok(mascotaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mascota> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(mascotaService.obtenerPorId(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Mascota>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(mascotaService.obtenerPorUsuario(usuarioId));
    }

    @PostMapping
    public ResponseEntity<Mascota> crear(@Valid @RequestBody Mascota mascota) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mascotaService.crear(mascota));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mascota> actualizar(@PathVariable Long id,
                                              @Valid @RequestBody Mascota mascota) {
        return ResponseEntity.ok(mascotaService.actualizar(id, mascota));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        mascotaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}