// AuthServiceImpl.java
package com.sanosysalvos.msusuarios.service;

import com.sanosysalvos.msusuarios.dto.LoginRequest;
import com.sanosysalvos.msusuarios.dto.LoginResponse;
import com.sanosysalvos.msusuarios.dto.RegistroRequest;
import com.sanosysalvos.msusuarios.model.Rol;
import com.sanosysalvos.msusuarios.model.Usuario;
import com.sanosysalvos.msusuarios.repository.RolRepository;
import com.sanosysalvos.msusuarios.repository.UsuarioRepository;
import com.sanosysalvos.msusuarios.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public LoginResponse login(LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()
                )
        );

        String token = jwtUtil.generarToken(request.getEmail());
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail()).get();

        Set<String> roles = usuario.getRoles().stream()
                .map(Rol::getNombre)
                .collect(Collectors.toSet());

        return new LoginResponse(
                token,
                "Bearer",
                usuario.getId(),
                usuario.getEmail(),
                usuario.getNombre(),
                roles
        );
    }

    @Override
    public Usuario registrar(RegistroRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado: " + request.getEmail());
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setEmail(request.getEmail());
        usuario.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        usuario.setTelefono(request.getTelefono());
        usuario.setComuna(request.getComuna());
        usuario.setRegion(request.getRegion());

        // Asignar rol USER por defecto
        Rol rolUser = rolRepository.findByNombre("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Rol ROLE_USER no encontrado en BD"));
        usuario.setRoles(Set.of(rolUser));

        return usuarioRepository.save(usuario);
    }
}