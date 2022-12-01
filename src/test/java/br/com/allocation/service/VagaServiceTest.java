package br.com.allocation.service;

import br.com.allocation.dto.vagaDTO.VagaCreateDTO;
import br.com.allocation.dto.vagaDTO.VagaDTO;
import br.com.allocation.entity.VagaEntity;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.repository.ProgramaRepository;
import br.com.allocation.repository.VagaRepository;
import br.com.allocation.service.factory.ClienteFactory;
import br.com.allocation.service.factory.ProgramaFactory;
import br.com.allocation.service.factory.VagaFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VagaServiceTest {
    @InjectMocks
    private VagaService vagaService;
    @Mock
    private VagaRepository vagaRepository;
    @Mock
    private ProgramaRepository programaRepository;
    @Mock
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private ProgramaService programaService;
    @Mock
    private ClienteService clienteService;

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(vagaService, "objectMapper", objectMapper);
    }

//    @Test
//    public void deveTestarSalvarComSucesso() throws RegraDeNegocioException {
//        VagaCreateDTO vagaCreateDTO = VagaFactory.getvagaCreateDTO();
//        VagaEntity vagaEntity = VagaFactory.getVagaEntity();
////        when(programaRepository
////                .findById(any())).thenReturn(Optional.of(ProgramaFactory.getProgramaEntity()));
////        qqqq
//        when(vagaRepository.save(any())).thenReturn(vagaEntity);
//        VagaDTO vagaDTO = vagaService.salvar(vagaCreateDTO);
//
//        assertNotNull(vagaDTO);
//        assertEquals("vaga", vagaCreateDTO.getNome());
//        Assertions.assertEquals("cocacolabr@mail.com.br",vagaCreateDTO.getEmailCliente());
//        Assertions.assertEquals(0, vagaDTO.getQuantidadeAlocados());
//
//    }

    @Test
    public void deveTestarListarComSucesso() {
    Integer pagina = 4;
    Integer quantidade = 10;

        VagaCreateDTO vagaCreateDTO = VagaFactory.getvagaCreateDTO();
        VagaEntity vagaEntity = VagaFactory.getVagaEntity();
    }

//    @Test
//    public void deveTestarEditarComSucesso() throws RegraDeNegocioException {
//    }
//
//    @Test
//    public void deveTestarDeleteComSucesso() throws RegraDeNegocioException {
//    }
//
//    @Test
//    public void deveTestarFindByIdComSucesso() throws RegraDeNegocioException {
//    }
//
//    @Test(expected = RegraDeNegocioException.class)
//    public void deveTestarFindByIdComErro() throws RegraDeNegocioException {
//    }
}
