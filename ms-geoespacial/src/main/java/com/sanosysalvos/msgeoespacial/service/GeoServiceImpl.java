// GeoServiceImpl.java
package com.sanosysalvos.msgeoespacial.service;

import com.sanosysalvos.msgeoespacial.dto.ReporteGeoDTO;
import com.sanosysalvos.msgeoespacial.repository.ReporteGeoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeoServiceImpl implements GeoService {

    private final ReporteGeoRepository repository;

    @Override
    public List<ReporteGeoDTO> buscarEnRadio(double lat, double lon, double radioKm) {
        double radioMetros = radioKm * 1000;
        log.info("Buscando reportes en radio {}km desde ({}, {})", radioKm, lat, lon);
        List<Object[]> resultados = repository.buscarEnRadio(lat, lon, radioMetros);
        return mapearResultados(resultados);
    }

    @Override
    public List<ReporteGeoDTO> buscarPerdidosEnRadio(double lat, double lon, double radioKm) {
        double radioMetros = radioKm * 1000;
        List<Object[]> resultados = repository.buscarPerdidosEnRadio(lat, lon, radioMetros);
        return mapearResultados(resultados);
    }

    @Override
    public List<ReporteGeoDTO> buscarPorComuna(String comuna) {
        return repository.findByComuna(comuna).stream()
                .map(r -> new ReporteGeoDTO(
                        r.getId(), r.getTipo(), r.getEstado(),
                        r.getComuna(), r.getDireccion(), r.getFechaEvento(),
                        r.getLatitud(), r.getLongitud(), null
                ))
                .collect(Collectors.toList());
    }

    // Mapear Object[] de la query nativa a DTO
    private List<ReporteGeoDTO> mapearResultados(List<Object[]> resultados) {
        return resultados.stream().map(row -> {
            Long id = row[0] != null ? ((Number) row[0]).longValue() : null;
            String tipo = row[1] != null ? String.valueOf(row[1]) : null;
            String estado = row[2] != null ? String.valueOf(row[2]) : null;
            String comuna = row[3] != null ? String.valueOf(row[3]) : null;
            String direccion = row[4] != null ? String.valueOf(row[4]) : null;
            LocalDate fecha = row[5] != null ? (LocalDate) row[5] : null;
            BigDecimal lat2 = row[6] != null ? new BigDecimal(row[6].toString()) : null;
            BigDecimal lon2 = row[7] != null ? new BigDecimal(row[7].toString()) : null;
            Double distancia = row[8] != null ? ((Number) row[8]).doubleValue() : null;

            return new ReporteGeoDTO(
                    id, tipo, estado, comuna, direccion, fecha, lat2, lon2, distancia
            );
        }).collect(Collectors.toList());
    }
}