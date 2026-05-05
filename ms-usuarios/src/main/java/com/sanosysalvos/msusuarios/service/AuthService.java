// AuthService.java
package com.sanosysalvos.msusuarios.service;

import com.sanosysalvos.msusuarios.dto.LoginRequest;
import com.sanosysalvos.msusuarios.dto.LoginResponse;
import com.sanosysalvos.msusuarios.dto.RegistroRequest;
import com.sanosysalvos.msusuarios.model.Usuario;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    Usuario registrar(RegistroRequest request);
}