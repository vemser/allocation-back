package br.com.allocation.repository;

import br.com.allocation.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, String> {
    FileEntity findById(Integer id);
}
