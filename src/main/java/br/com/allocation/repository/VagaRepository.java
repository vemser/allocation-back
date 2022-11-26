package br.com.allocation.repository;

import br.com.allocation.entity.VagaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VagaRepository extends JpaRepository<VagaEntity, Integer> {
}
