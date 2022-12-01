package br.com.allocation.repository;

import br.com.allocation.entity.AvaliacaoEntity;
import br.com.allocation.enums.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AvaliacaoRepository extends JpaRepository<AvaliacaoEntity, Integer> {
}
