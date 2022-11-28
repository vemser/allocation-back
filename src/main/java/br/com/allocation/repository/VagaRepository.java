package br.com.allocation.repository;

import br.com.allocation.dto.vagaDTO.VagaDTO;
import br.com.allocation.entity.VagaEntity;
import br.com.allocation.enums.Situacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VagaRepository extends JpaRepository<VagaEntity, Integer> {

    List<VagaEntity> findBySituacao(Situacao situacao);

}
