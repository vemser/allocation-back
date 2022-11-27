package br.com.allocation.service;

import br.com.allocation.entity.CargoEntity;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.repository.CargoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CargoService {
    private final CargoRepository cargoRepository;
    public CargoEntity findByIdCargo(Integer idCargo) {
        return cargoRepository.findByIdCargo(idCargo);
    }
    public CargoEntity findByNome(String nome) throws RegraDeNegocioException {
        return cargoRepository.findByNome(nome).orElseThrow(() -> new RegraDeNegocioException("Cargo não encontrado"));
    }
}
