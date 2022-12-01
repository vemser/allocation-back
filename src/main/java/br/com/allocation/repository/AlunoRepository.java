package br.com.allocation.repository;

import br.com.allocation.entity.AlunoEntity;
import br.com.allocation.enums.StatusAluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface AlunoRepository extends JpaRepository<AlunoEntity, Integer> {
    Set<AlunoEntity> findAllByStatusAluno(StatusAluno statusAluno);
    Optional<AlunoEntity> findByEmailIgnoreCase(String email);
    Optional<AlunoEntity> findByNome(String nome);
}
