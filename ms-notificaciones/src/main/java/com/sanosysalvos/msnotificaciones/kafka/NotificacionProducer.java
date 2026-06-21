package com.sanosysalvos.msnotificaciones.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Productor Kafka para publicar eventos de notificacion enviada.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class NotificacionProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void publicarNotificacionEnviada(Long usuarioId, String tipo) {
        String mensaje = String.format("{\"usuarioId\":%d,\"tipo\":\"%s\"}", usuarioId, tipo);
        kafkaTemplate.send("notificaciones-enviadas", mensaje);
        log.info("Evento publicado en [notificaciones-enviadas]: {}", mensaje);
    }
}
