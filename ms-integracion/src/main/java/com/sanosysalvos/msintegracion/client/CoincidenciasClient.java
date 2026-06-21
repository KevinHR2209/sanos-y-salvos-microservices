package com.sanosysalvos.msintegracion.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * Cliente Feign para comunicarse con ms-coincidencias via Eureka.
 */
@FeignClient(name = "ms-coincidencias")
public interface CoincidenciasClient {

    @GetMapping("/api/v1/coincidencias/pendientes")
    List<Map<String, Object>> listarPendientes();

    @PostMapping("/api/v1/coincidencias/buscar")
    List<Map<String, Object>> ejecutarBusqueda();
}
