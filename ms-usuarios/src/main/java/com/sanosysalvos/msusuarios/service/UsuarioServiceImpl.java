// UsuarioServiceImpl.java
package com.sanosysalvos.msusuarios.service;

import com.sanosysalvos.msusuarios.dto.UsuarioDTO;
import com.sanosysalvos.msusuarios.exception.ResourceNotFoundException;
import com.sanosysalvos.msusuarios.model.Rol;
import com.sanosysalvos.msusuarios.model.Usuario;
import com.sanosysalvos.msusuarios.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDTO obtenerPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + id));
        return toDTO(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDTO obtenerPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + email));
        return toDTO(usuario);
    }

    @Override
    @Transactional
    public UsuarioDTO actualizar(Long id, UsuarioDTO datos) {
        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + id));
        existente.setNombre(datos.getNombre());
        existente.setApellido(datos.getApellido());
        existente.setTelefono(datos.getTelefono());
        existente.setComuna(datos.getComuna());
        existente.setRegion(datos.getRegion());
        return toDTO(usuarioRepository.save(existente));
    }

    @Override
    @Transactional
    public void desactivar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + id));
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }

    // Mapper manual para no exponer passwordHash ni datos sensibles
    private UsuarioDTO toDTO(Usuario u) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(u.getId());
        dto.setNombre(u.getNombre());
        dto.setApellido(u.getApellido());
        dto.setEmail(u.getEmail());
        dto.setTelefono(u.getTelefono());
        dto.setComuna(u.getComuna());
        dto.setRegion(u.getRegion());
        dto.setActivo(u.getActivo());
        dto.setRoles(u.getRoles().stream()
                .map(Rol::getNombre)
                .collect(Collectors.toSet()));
        dto.setCreadoEn(u.getCreadoEn());
        return dto;
    }
}
