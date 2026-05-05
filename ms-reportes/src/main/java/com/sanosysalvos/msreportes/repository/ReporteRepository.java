// ReporteRepository.java
package com.sanosysalvos.msreportes.repository;

import com.sanosysalvos.msreportes.model.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {
    List<Reporte> findByTipo(String tipo);
    List<Reporte> findByEstado(String estado);
    List<Reporte> findByComunaIgnoreCase(String comuna);
    List<Reporte> findByComuna(String comuna);
    List<Reporte> findByUsuarioId(Long usuarioId);
    List<Reporte> findByTipoAndEstado(String tipo, String estado);
}