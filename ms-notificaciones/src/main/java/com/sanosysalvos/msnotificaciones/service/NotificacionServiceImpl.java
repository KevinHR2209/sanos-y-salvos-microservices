package com.sanosysalvos.msnotificaciones.service;

import com.sanosysalvos.msnotificaciones.dto.NotificacionDTO;
import com.sanosysalvos.msnotificaciones.model.Notificacion;
import com.sanosysalvos.msnotificaciones.repository.NotificacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificacionServiceImpl implements NotificacionService {

    private final NotificacionRepository notificacionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<NotificacionDTO> obtenerPorUsuario(Long usuarioId) {
        return notificacionRepository.findByUsuarioIdOrderByCreadoEnDesc(usuarioId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificacionDTO> obtenerNoLeidasPorUsuario(Long usuarioId) {
        return notificacionRepository.findByUsuarioIdAndLeidaFalseOrderByCreadoEnDesc(usuarioId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public NotificacionDTO marcarComoLeida(Long id) {
        Notificacion n = notificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificacion no encontrada: " + id));
        n.setLeida(true);
        n.setLeidaEn(LocalDateTime.now());
        return toDTO(notificacionRepository.save(n));
    }

    @Override
    @Transactional
    public NotificacionDTO crear(NotificacionDTO dto) {
        Notificacion n = new Notificacion();
        n.setUsuarioId(dto.getUsuarioId());
        n.setTipo(dto.getTipo());
        n.setMensaje(dto.getMensaje());
        n.setReporteId(dto.getReporteId());
        n.setCoincidenciaId(dto.getCoincidenciaId());
        return toDTO(notificacionRepository.save(n));
    }

    private NotificacionDTO toDTO(Notificacion n) {
        NotificacionDTO dto = new NotificacionDTO();
        dto.setId(n.getId());
        dto.setUsuarioId(n.getUsuarioId());
        dto.setTipo(n.getTipo());
        dto.setMensaje(n.getMensaje());
        dto.setLeida(n.getLeida());
        dto.setReporteId(n.getReporteId());
        dto.setCoincidenciaId(n.getCoincidenciaId());
        dto.setCreadoEn(n.getCreadoEn());
        dto.setLeidaEn(n.getLeidaEn());
        return dto;
    }
}
