// UsuarioRepository.java
package com.sanosysalvos.msusuarios.repository;

import com.sanosysalvos.msusuarios.model.Usuario;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Carga los roles junto con el usuario en una sola query (evita LazyInitializationException)
    @EntityGraph(attributePaths = {"roles"})
    Optional<Usuario> findByEmail(String email);

    Boolean existsByEmail(String email);
}
