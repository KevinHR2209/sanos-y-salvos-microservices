package com.sanosysalvos.msgeoespacial.repository;

import com.sanosysalvos.msgeoespacial.model.ReporteGeo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReporteGeoRepository extends JpaRepository<ReporteGeo, Long> {

    @Query(value = """
        SELECT 
            r.id,
            r.tipo,
            r.estado,
            r.comuna,
            r.direccion,
            r.fecha_evento,
            r.latitud,
            r.longitud,
            ST_Distance(
                r.ubicacion::geography,
                ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)::geography
            ) / 1000.0 AS distancia_km
        FROM reportes r
        WHERE r.ubicacion IS NOT NULL
          AND r.estado = 'ACTIVO'
          AND ST_DWithin(
              r.ubicacion::geography,
              ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)::geography,
              :radioMetros
          )
        ORDER BY distancia_km ASC
        """, nativeQuery = true)
    List<Object[]> buscarEnRadio(@Param("lat") double lat,
                                 @Param("lon") double lon,
                                 @Param("radioMetros") double radioMetros);

    @Query(value = """
        SELECT 
            r.id,
            r.tipo,
            r.estado,
            r.comuna,
            r.direccion,
            r.fecha_evento,
            r.latitud,
            r.longitud,
            ST_Distance(
                r.ubicacion::geography,
                ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)::geography
            ) / 1000.0 AS distancia_km
        FROM reportes r
        WHERE r.ubicacion IS NOT NULL
          AND r.tipo = 'PERDIDO'
          AND r.estado = 'ACTIVO'
          AND ST_DWithin(
              r.ubicacion::geography,
              ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)::geography,
              :radioMetros
          )
        ORDER BY distancia_km ASC
        """, nativeQuery = true)
    List<Object[]> buscarPerdidosEnRadio(@Param("lat") double lat,
                                         @Param("lon") double lon,
                                         @Param("radioMetros") double radioMetros);

    List<ReporteGeo> findByComuna(String comuna);
    List<ReporteGeo> findByTipoAndEstado(String tipo, String estado);
}