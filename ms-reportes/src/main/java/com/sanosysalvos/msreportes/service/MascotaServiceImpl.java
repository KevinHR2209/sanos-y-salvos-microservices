// MascotaServiceImpl.java
package com.sanosysalvos.msreportes.service;

import com.sanosysalvos.msreportes.exception.ResourceNotFoundException;
import com.sanosysalvos.msreportes.model.Mascota;
import com.sanosysalvos.msreportes.repository.MascotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MascotaServiceImpl implements MascotaService {

    private final MascotaRepository mascotaRepository;

    @Override
    public List<Mascota> listarTodas() {
        return mascotaRepository.findAll();
    }

    @Override
    public Mascota obtenerPorId(Long id) {
        return mascotaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mascota no encontrada con id: " + id));
    }

    @Override
    public Mascota crear(Mascota mascota) {
        return mascotaRepository.save(mascota);
    }

    @Override
    public Mascota actualizar(Long id, Mascota datos) {
        Mascota existente = obtenerPorId(id);
        existente.setNombre(datos.getNombre());
        existente.setEspecie(datos.getEspecie());
        existente.setRaza(datos.getRaza());
        existente.setColor(datos.getColor());
        existente.setTamanio(datos.getTamanio());
        existente.setSexo(datos.getSexo());
        existente.setDescripcion(datos.getDescripcion());
        existente.setTieneMicrochip(datos.getTieneMicrochip());
        existente.setNumeroMicrochip(datos.getNumeroMicrochip());
        existente.setFotoUrl(datos.getFotoUrl());
        return mascotaRepository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        obtenerPorId(id); // valida que exista
        mascotaRepository.deleteById(id);
    }

    @Override
    public List<Mascota> obtenerPorUsuario(Long usuarioId) {
        return mascotaRepository.findByUsuarioId(usuarioId);
    }
}