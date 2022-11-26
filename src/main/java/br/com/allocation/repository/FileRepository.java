package br.com.allocation.repository;

import br.com.allocation.entity.FileEntity;
import br.com.allocation.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, String> {
    FileEntity findByUsuario(UsuarioEntity usuario);
}
