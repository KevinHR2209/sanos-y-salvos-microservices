// GeoService.java
package com.sanosysalvos.msgeoespacial.service;

import com.sanosysalvos.msgeoespacial.dto.ReporteGeoDTO;
import java.util.List;

public interface GeoService {
    List<ReporteGeoDTO> buscarEnRadio(double lat, double lon, double radioKm);
    List<ReporteGeoDTO> buscarPerdidosEnRadio(double lat, double lon, double radioKm);
    List<ReporteGeoDTO> buscarPorComuna(String comuna);
}