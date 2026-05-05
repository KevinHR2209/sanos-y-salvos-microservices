package com.sanosysalvos.msreportes.mapper;

import com.sanosysalvos.msreportes.dto.ReporteDTO;
import com.sanosysalvos.msreportes.model.Mascota;
import com.sanosysalvos.msreportes.model.Reporte;

public class ReporteMapper {

    public static ReporteDTO toDTO(Reporte reporte) {
        Mascota mascota = reporte.getMascota();

        return new ReporteDTO(
                reporte.getId(),
                reporte.getTipo(),
                reporte.getEstado(),
                reporte.getUsuarioId(),
                reporte.getLatitud(),
                reporte.getLongitud(),
                reporte.getComuna(),
                reporte.getFechaEvento(),
                mascota != null ? mascota.getEspecie() : null,
                mascota != null ? mascota.getRaza() : null,
                mascota != null ? mascota.getColor() : null,
                mascota != null ? mascota.getTamanio() : null,
                mascota != null ? mascota.getSexo() : null
        );
    }
}