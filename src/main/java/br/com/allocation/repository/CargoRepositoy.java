package br.com.allocation.repository;

import br.com.allocation.entity.CargoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoRepositoy extends JpaRepository<CargoEntity, Integer> {
}
