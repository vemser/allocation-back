package br.com.allocation.repository;

import br.com.allocation.entity.CargoEntity;
import br.com.allocation.entity.UsuarioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {
    Page<UsuarioEntity> findAllByNomeCompletoContainingIgnoreCase(Pageable pageable, String email);

    Page<UsuarioEntity> findAllByCargosContainingIgnoreCase(Pageable pageable, CargoEntity cargos);

    Optional<UsuarioEntity> findByEmailIgnoreCase(String email);

    Page<UsuarioEntity> findAllByEmail(Pageable pageable, String email);
}
