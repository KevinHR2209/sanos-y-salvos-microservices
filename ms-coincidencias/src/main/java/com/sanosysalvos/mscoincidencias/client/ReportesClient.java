// ReportesClient.java
package com.sanosysalvos.mscoincidencias.client;

import com.sanosysalvos.mscoincidencias.dto.ReporteClienteDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReportesClient {

    @Value("${ms.reportes.url}")
    private String reportesUrl;

    private final RestTemplate restTemplate;

    public List<ReporteClienteDTO> obtenerReportesPorTipo(String tipo) {
        String url = reportesUrl + "/api/v1/reportes/tipo/" + tipo;
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ReporteClienteDTO>>() {}
        ).getBody();
    }
}