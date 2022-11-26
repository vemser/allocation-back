package br.com.allocation.repository;

import br.com.allocation.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {

    Optional<UsuarioEntity> findByEmail(String email);
}
