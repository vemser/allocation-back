package br.com.allocation.repository;

import br.com.allocation.entity.AlunoEntity;
import br.com.allocation.entity.AvaliacaoEntity;
import br.com.allocation.enums.Situacao;
import br.com.allocation.enums.StatusAluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AvaliacaoRepository extends JpaRepository<AvaliacaoEntity, Integer> {
//    Set<AvaliacaoEntity> findAllByStatusAvalicao(Situacao situacao);
}
