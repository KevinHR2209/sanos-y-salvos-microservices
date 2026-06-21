package com.sanosysalvos.msnotificaciones.service;

import com.sanosysalvos.msnotificaciones.dto.NotificacionDTO;
import java.util.List;

public interface NotificacionService {
    List<NotificacionDTO> obtenerPorUsuario(Long usuarioId);
    List<NotificacionDTO> obtenerNoLeidasPorUsuario(Long usuarioId);
    NotificacionDTO marcarComoLeida(Long id);
    NotificacionDTO crear(NotificacionDTO dto);
}
