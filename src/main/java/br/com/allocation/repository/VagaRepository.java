package br.com.allocation.repository;

import br.com.allocation.entity.VagaEntity;
import br.com.allocation.enums.Situacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VagaRepository extends JpaRepository<VagaEntity, Integer> {

    List<VagaEntity> findBySituacao(Situacao situacao);
    Optional<VagaEntity> findByNome(String nome);

}
