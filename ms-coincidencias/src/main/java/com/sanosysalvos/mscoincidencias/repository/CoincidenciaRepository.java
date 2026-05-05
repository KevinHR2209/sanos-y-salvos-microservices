// CoincidenciaRepository.java
package com.sanosysalvos.mscoincidencias.repository;

import com.sanosysalvos.mscoincidencias.model.Coincidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CoincidenciaRepository extends JpaRepository<Coincidencia, Long> {
    List<Coincidencia> findByEstado(String estado);
    List<Coincidencia> findByReportePerdidoId(Long reportePerdidoId);
    boolean existsByReportePerdidoIdAndReporteEncontradoId(Long perdidoId, Long encontradoId);
}