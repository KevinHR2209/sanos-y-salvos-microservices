package com.sanosysalvos.msnotificaciones.repository;

import com.sanosysalvos.msnotificaciones.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    List<Notificacion> findByUsuarioIdOrderByCreadoEnDesc(Long usuarioId);
    List<Notificacion> findByUsuarioIdAndLeidaFalseOrderByCreadoEnDesc(Long usuarioId);
}
