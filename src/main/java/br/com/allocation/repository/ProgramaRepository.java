package br.com.allocation.repository;

import br.com.allocation.entity.ProgramaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProgramaRepository extends JpaRepository<ProgramaEntity, Integer> {

    Optional<ProgramaEntity> findByNome(String nome);
}
