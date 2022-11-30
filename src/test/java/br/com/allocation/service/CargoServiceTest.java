package br.com.allocation.service;

import br.com.allocation.entity.CargoEntity;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.repository.CargoRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

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
    public void deveTestarFindByIdComSucesso(){
        //SETUP
        Integer id = 1;
        CargoEntity cargo = getCargoEntity();
        when(cargoRepository.findByIdCargo(anyInt())).thenReturn(cargo);

        //ACT
        CargoEntity cargoEntity = cargoService.findByIdCargo(id);
        //ASSERT
        assertNotNull(cargoEntity);
        assertEquals(1, cargoEntity.getIdCargo());
    }

    @Test
    public void deveTestarFindByNomeComSucesso() throws RegraDeNegocioException {
        //SETUP
        String nome = "ADMIN";
        CargoEntity cargo = getCargoEntity();
        when(cargoRepository.findByNome(anyString())).thenReturn(Optional.of(cargo));
        //ACT
        CargoEntity cargoEntity = cargoService.findByNome(nome);

        //ASSERT
        assertNotNull(cargoEntity);
        assertEquals(cargoEntity.getNome(), cargo.getNome());
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarFindByNomeComErro() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        String nome = "GENERAL";


        // Ação (ACT)
        CargoEntity cargo = cargoService.findByNome(nome);

        //Assert
        assertNull(cargo);
    }

    private static CargoEntity getCargoEntity(){
        CargoEntity cargo = new CargoEntity();
        cargo.setIdCargo(1);
        cargo.setNome("ADMIN");
        return cargo;
    }
}
