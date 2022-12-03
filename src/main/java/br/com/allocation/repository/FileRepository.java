package br.com.allocation.repository;

import br.com.allocation.entity.FileEntity;
import br.com.allocation.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity, String> {
    Optional<FileEntity> findByUsuario(UsuarioEntity usuario);

    FileEntity findFileEntitiesByUsuario (UsuarioEntity usuario);
    void deleteByUsuario(UsuarioEntity usuario);
}
