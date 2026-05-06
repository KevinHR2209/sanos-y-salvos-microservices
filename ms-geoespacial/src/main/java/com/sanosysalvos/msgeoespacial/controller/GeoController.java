// GeoController.java
package com.sanosysalvos.msgeoespacial.controller;

import com.sanosysalvos.msgeoespacial.dto.ReporteGeoDTO;
import com.sanosysalvos.msgeoespacial.service.GeoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/geo")
@RequiredArgsConstructor
public class GeoController {

    private final GeoService geoService;

    /**
     * Busca TODOS los reportes activos en un radio dado
     * Ejemplo: GET /api/v1/geo/radio?lat=-33.4569&lon=-70.6483&radioKm=5
     */
    @GetMapping("/radio")
    public ResponseEntity<List<ReporteGeoDTO>> buscarEnRadio(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam(defaultValue = "5.0") double radioKm) {
        return ResponseEntity.ok(geoService.buscarEnRadio(lat, lon, radioKm));
    }

    /**
     * Busca solo mascotas PERDIDAS en un radio dado
     * Ejemplo: GET /api/v1/geo/perdidos/radio?lat=-33.4569&lon=-70.6483&radioKm=3
     */
    @GetMapping("/perdidos/radio")
    public ResponseEntity<List<ReporteGeoDTO>> buscarPerdidosEnRadio(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam(defaultValue = "3.0") double radioKm) {
        return ResponseEntity.ok(geoService.buscarPerdidosEnRadio(lat, lon, radioKm));
    }

    /**
     * Busca reportes por comuna
     * Ejemplo: GET /api/v1/geo/comuna/Providencia
     */
    @GetMapping("/comuna/{comuna}")
    public ResponseEntity<List<ReporteGeoDTO>> buscarPorComuna(
            @PathVariable String comuna) {
        return ResponseEntity.ok(geoService.buscarPorComuna(comuna));
    }
}