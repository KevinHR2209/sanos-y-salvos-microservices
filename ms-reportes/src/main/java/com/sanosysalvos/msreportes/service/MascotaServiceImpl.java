// MascotaServiceImpl.java
package com.sanosysalvos.msreportes.service;

import com.sanosysalvos.msreportes.dto.MascotaDTO;
import com.sanosysalvos.msreportes.dto.MascotaRequestDTO;
import com.sanosysalvos.msreportes.exception.ResourceNotFoundException;
import com.sanosysalvos.msreportes.model.Mascota;
import com.sanosysalvos.msreportes.repository.MascotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MascotaServiceImpl implements MascotaService {

    private final MascotaRepository mascotaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MascotaDTO> listarTodas() {
        return mascotaRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public MascotaDTO obtenerPorId(Long id) {
        return toDTO(findOrThrow(id));
    }

    @Override
    @Transactional
    public MascotaDTO crear(MascotaRequestDTO request) {
        Mascota mascota = fromRequest(request, new Mascota());
        return toDTO(mascotaRepository.save(mascota));
    }

    @Override
    @Transactional
    public MascotaDTO actualizar(Long id, MascotaRequestDTO request) {
        Mascota existente = findOrThrow(id);
        fromRequest(request, existente);
        return toDTO(mascotaRepository.save(existente));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        findOrThrow(id);
        mascotaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MascotaDTO> obtenerPorUsuario(Long usuarioId) {
        return mascotaRepository.findByUsuarioId(usuarioId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ---- helpers privados ----

    private Mascota findOrThrow(Long id) {
        return mascotaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mascota no encontrada con id: " + id));
    }

    private Mascota fromRequest(MascotaRequestDTO req, Mascota mascota) {
        mascota.setNombre(req.getNombre());
        mascota.setEspecie(req.getEspecie());
        mascota.setRaza(req.getRaza());
        mascota.setColor(req.getColor());
        mascota.setTamanio(req.getTamanio());
        mascota.setSexo(req.getSexo());
        mascota.setDescripcion(req.getDescripcion());
        mascota.setTieneMicrochip(req.getTieneMicrochip());
        mascota.setNumeroMicrochip(req.getNumeroMicrochip());
        mascota.setFotoUrl(req.getFotoUrl());
        mascota.setUsuarioId(req.getUsuarioId());
        return mascota;
    }

    private MascotaDTO toDTO(Mascota m) {
        MascotaDTO dto = new MascotaDTO();
        dto.setId(m.getId());
        dto.setNombre(m.getNombre());
        dto.setEspecie(m.getEspecie());
        dto.setRaza(m.getRaza());
        dto.setColor(m.getColor());
        dto.setTamanio(m.getTamanio());
        dto.setSexo(m.getSexo());
        dto.setDescripcion(m.getDescripcion());
        dto.setTieneMicrochip(m.getTieneMicrochip());
        dto.setNumeroMicrochip(m.getNumeroMicrochip());
        dto.setFotoUrl(m.getFotoUrl());
        dto.setUsuarioId(m.getUsuarioId());
        dto.setCreadoEn(m.getCreadoEn());
        dto.setActualizadoEn(m.getActualizadoEn());
        return dto;
    }
}
