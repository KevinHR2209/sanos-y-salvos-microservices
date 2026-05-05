// MotorMatchService.java
package com.sanosysalvos.mscoincidencias.service;

import com.sanosysalvos.mscoincidencias.dto.ReporteClienteDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

@Service
public class MotorMatchService {

    /**
     * Calcula un puntaje de similitud entre un reporte PERDIDO y uno ENCONTRADO.
     * Retorna valor entre 0.00 y 100.00
     */
    public BigDecimal calcularPuntaje(ReporteClienteDTO perdido,
                                      ReporteClienteDTO encontrado) {
        double puntaje = 0.0;
        Map<String, String> criterios = new HashMap<>();

        // Criterio 1: Especie (obligatorio — peso 40%)
        if (coincide(perdido.getMascotaEspecie(), encontrado.getMascotaEspecie())) {
            puntaje += 40;
            criterios.put("especie", "COINCIDE");
        } else {
            criterios.put("especie", "NO_COINCIDE");
            // Si la especie no coincide, retornamos 0 directo
            return BigDecimal.ZERO;
        }

        // Criterio 2: Color (peso 20%)
        if (coincide(perdido.getMascotaColor(), encontrado.getMascotaColor())) {
            puntaje += 20;
            criterios.put("color", "COINCIDE");
        } else {
            criterios.put("color", "NO_COINCIDE");
        }

        // Criterio 3: Raza (peso 15%)
        if (coincide(perdido.getMascotaRaza(), encontrado.getMascotaRaza())) {
            puntaje += 15;
            criterios.put("raza", "COINCIDE");
        } else {
            criterios.put("raza", "NO_COINCIDE");
        }

        // Criterio 4: Tamaño (peso 10%)
        if (coincide(perdido.getMascotaTamanio(), encontrado.getMascotaTamanio())) {
            puntaje += 10;
            criterios.put("tamanio", "COINCIDE");
        } else {
            criterios.put("tamanio", "NO_COINCIDE");
        }

        // Criterio 5: Sexo (peso 10%)
        if (coincide(perdido.getMascotaSexo(), encontrado.getMascotaSexo())) {
            puntaje += 10;
            criterios.put("sexo", "COINCIDE");
        } else {
            criterios.put("sexo", "NO_COINCIDE");
        }

        // Criterio 6: Proximidad geográfica (peso 5%)
        if (mismaComuna(perdido.getComuna(), encontrado.getComuna())) {
            puntaje += 5;
            criterios.put("comuna", "COINCIDE");
        } else {
            criterios.put("comuna", "DIFERENTE");
        }

        return BigDecimal.valueOf(puntaje).setScale(2, RoundingMode.HALF_UP);
    }

    public String criteriosAJson(ReporteClienteDTO perdido, ReporteClienteDTO encontrado) {
        return String.format(
                "{\"especie\":\"%s\",\"color\":\"%s\",\"raza\":\"%s\",\"tamanio\":\"%s\",\"sexo\":\"%s\",\"comuna\":\"%s\"}",
                igualdad(perdido.getMascotaEspecie(), encontrado.getMascotaEspecie()),
                igualdad(perdido.getMascotaColor(), encontrado.getMascotaColor()),
                igualdad(perdido.getMascotaRaza(), encontrado.getMascotaRaza()),
                igualdad(perdido.getMascotaTamanio(), encontrado.getMascotaTamanio()),
                igualdad(perdido.getMascotaSexo(), encontrado.getMascotaSexo()),
                igualdad(perdido.getComuna(), encontrado.getComuna())
        );
    }

    private boolean coincide(String a, String b) {
        if (a == null || b == null) return false;
        return a.trim().equalsIgnoreCase(b.trim());
    }

    private boolean mismaComuna(String a, String b) {
        return coincide(a, b);
    }

    private String igualdad(String a, String b) {
        return coincide(a, b) ? "COINCIDE" : "NO_COINCIDE";
    }
}