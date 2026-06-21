package com.sanosysalvos.msnotificaciones.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanosysalvos.msnotificaciones.dto.NotificacionDTO;
import com.sanosysalvos.msnotificaciones.service.NotificacionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Consumidor Kafka que escucha eventos de coincidencias y reportes
 * para generar notificaciones automaticas a los usuarios.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class NotificacionConsumer {

    private final NotificacionService notificacionService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "coincidencias-encontradas", groupId = "notificaciones-group")
    public void consumirCoincidencia(String mensaje) {
        try {
            log.info("Evento Kafka recibido [coincidencias-encontradas]: {}", mensaje);
            NotificacionDTO dto = objectMapper.readValue(mensaje, NotificacionDTO.class);
            dto.setTipo("COINCIDENCIA_ENCONTRADA");
            notificacionService.crear(dto);
        } catch (Exception e) {
            log.error("Error al procesar evento de coincidencia: {}", e.getMessage());
        }
    }

    @KafkaListener(topics = "reportes-actualizados", groupId = "notificaciones-group")
    public void consumirActualizacionReporte(String mensaje) {
        try {
            log.info("Evento Kafka recibido [reportes-actualizados]: {}", mensaje);
            NotificacionDTO dto = objectMapper.readValue(mensaje, NotificacionDTO.class);
            dto.setTipo("REPORTE_ACTUALIZADO");
            notificacionService.crear(dto);
        } catch (Exception e) {
            log.error("Error al procesar evento de reporte: {}", e.getMessage());
        }
    }
}
