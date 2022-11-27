package br.com.allocation.repository;

import br.com.allocation.entity.CargoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CargoRepository extends JpaRepository<CargoEntity, Integer> {
    CargoEntity findByIdCargo(Integer idCargo);
    Optional<CargoEntity> findByNome(String nome);
}
