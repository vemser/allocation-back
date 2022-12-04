package br.com.allocation.repository;

import br.com.allocation.entity.AlunoEntity;
import br.com.allocation.enums.Situacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<AlunoEntity, Integer> {
    Page<AlunoEntity> findAllBySituacao(Pageable pageable, Situacao situacao);

    Optional<AlunoEntity> findByEmailIgnoreCase(String email);

    Optional<AlunoEntity> findByNome(String nome);

    Page<AlunoEntity> findAllByNomeContainingIgnoreCase(Pageable pageable, String nome);

    Page<AlunoEntity> findAllByEmailIgnoreCase(Pageable pageable, String email);
}
