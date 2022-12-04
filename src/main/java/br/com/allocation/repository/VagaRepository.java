package br.com.allocation.repository;

import br.com.allocation.entity.VagaEntity;
import br.com.allocation.enums.Situacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VagaRepository extends JpaRepository<VagaEntity, Integer> {

    List<VagaEntity> findBySituacao(Situacao situacao);

    Page<VagaEntity> findAllByNomeContainingIgnoreCase(Pageable pageable, String nome);

    Optional<VagaEntity> findByNome(String nome);

}
