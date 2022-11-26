package br.com.allocation.repository;

import br.com.allocation.entity.ReservaAlocacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaAlocacaoRepository extends JpaRepository<ReservaAlocacaoEntity, Integer> {
}
