// MascotaController.java
package com.sanosysalvos.msreportes.controller;

import com.sanosysalvos.msreportes.dto.MascotaDTO;
import com.sanosysalvos.msreportes.dto.MascotaRequestDTO;
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
    public ResponseEntity<List<MascotaDTO>> listarTodas() {
        return ResponseEntity.ok(mascotaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MascotaDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(mascotaService.obtenerPorId(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<MascotaDTO>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(mascotaService.obtenerPorUsuario(usuarioId));
    }

    @PostMapping
    public ResponseEntity<MascotaDTO> crear(@Valid @RequestBody MascotaRequestDTO mascotaRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mascotaService.crear(mascotaRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MascotaDTO> actualizar(@PathVariable Long id,
                                                 @Valid @RequestBody MascotaRequestDTO mascotaRequest) {
        return ResponseEntity.ok(mascotaService.actualizar(id, mascotaRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        mascotaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
