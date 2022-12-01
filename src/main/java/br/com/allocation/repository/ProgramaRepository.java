package br.com.allocation.repository;

import br.com.allocation.entity.ProgramaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgramaRepository extends JpaRepository<ProgramaEntity, Integer> {

    Optional<ProgramaEntity> findByNomeContainingIgnoreCase(String nome);
    Page<ProgramaEntity> findAllByNomeContainingIgnoreCase(String nome, Pageable pageabl);
}
