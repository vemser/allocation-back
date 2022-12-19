package br.com.allocation.service;

import br.com.allocation.entity.CargoEntity;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.repository.CargoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CargoServiceTest {

    @InjectMocks
    private CargoService cargoService;

    @Mock
    private CargoRepository cargoRepository;

    @Test
    public void deveTestarFindByIdComSucesso() {
        Integer id = 1;
        CargoEntity cargo = getCargoEntity();
        when(cargoRepository.findByIdCargo(anyInt())).thenReturn(cargo);

        CargoEntity cargoEntity = cargoService.findByIdCargo(id);

        assertNotNull(cargoEntity);
        assertEquals(1, cargoEntity.getIdCargo());
    }

    @Test
    public void deveTestarFindByNomeComSucesso() throws RegraDeNegocioException {
        String nome = "ADMIN";
        CargoEntity cargo = getCargoEntity();
        when(cargoRepository.findByNome(anyString())).thenReturn(Optional.of(cargo));

        CargoEntity cargoEntity = cargoService.findByNome(nome);

        assertNotNull(cargoEntity);
        assertEquals(cargoEntity.getNome(), cargo.getNome());
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarFindByNomeComErro() throws RegraDeNegocioException {
        String nome = "GENERAL";

        CargoEntity cargo = cargoService.findByNome(nome);
    }

    private static CargoEntity getCargoEntity() {
        CargoEntity cargo = new CargoEntity();
        cargo.setIdCargo(1);
        cargo.setNome("ADMIN");
        return cargo;
    }
}
