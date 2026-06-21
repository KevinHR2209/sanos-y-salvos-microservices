package com.sanosysalvos.msintegracion.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

/**
 * Cliente Feign para comunicarse con ms-reportes via Eureka.
 * El Circuit Breaker de Resilience4j protege estas llamadas.
 */
@FeignClient(name = "ms-reportes")
public interface ReportesClient {

    @GetMapping("/api/v1/reportes")
    List<Map<String, Object>> listarReportes();

    @GetMapping("/api/v1/reportes/{id}")
    Map<String, Object> obtenerReportePorId(@PathVariable Long id);

    @GetMapping("/api/v1/reportes/perdidos")
    List<Map<String, Object>> listarPerdidos();
}
