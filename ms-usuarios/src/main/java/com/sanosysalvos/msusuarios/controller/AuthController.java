// AuthController.java
package com.sanosysalvos.msusuarios.controller;

import com.sanosysalvos.msusuarios.dto.LoginRequest;
import com.sanosysalvos.msusuarios.dto.LoginResponse;
import com.sanosysalvos.msusuarios.dto.RegistroRequest;
import com.sanosysalvos.msusuarios.model.Usuario;
import com.sanosysalvos.msusuarios.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/registro")
    public ResponseEntity<Usuario> registrar(@Valid @RequestBody RegistroRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registrar(request));
    }
}