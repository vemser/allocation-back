package br.com.allocation.service;

import br.com.allocation.dto.reservaAlocacaoDTO.ReservaAlocacaoCreateDTO;
import br.com.allocation.dto.reservaAlocacaoDTO.ReservaAlocacaoDTO;
import br.com.allocation.entity.AlunoEntity;
import br.com.allocation.entity.ReservaAlocacaoEntity;
import br.com.allocation.enums.StatusAluno;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.repository.AlunoRepository;
import br.com.allocation.repository.ReservaAlocacaoRepository;
import br.com.allocation.service.factory.AlunoFactory;
import br.com.allocation.service.factory.ReservaAlocacaoFactory;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReservaAlocacaoServiceTest {

    @InjectMocks
    private ReservaAlocacaoService reservaAlocacaoService;

    @Mock
    private ReservaAlocacaoRepository reservaAlocacaoRepository;

    @Mock
    private VagaService vagaService;

    @Mock
    private AlunoService alunoService;

    @Mock
    private AvaliacaoService avaliacaoService;

    @Mock
    private AlunoRepository alunoRepository;

    @Test
    public void deveTestarSalvarComSucesso() throws RegraDeNegocioException {
        //SETUP
        ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO = ReservaAlocacaoFactory.getReservaAlocacaoCreateDTO();
        ReservaAlocacaoEntity reservaAlocacaoEntity = ReservaAlocacaoFactory.getReservaAlocacaoEntity();
        AlunoEntity aluno = AlunoFactory.getAlunoEntity();
        when(alunoService.alterarStatusAluno(any(), any())).thenReturn(aluno);
        when(reservaAlocacaoRepository.save(any())).thenReturn(reservaAlocacaoEntity);
        when(alunoService.findById(anyInt())).thenReturn(aluno);
        //ACT
        ReservaAlocacaoDTO reservaAlocacaoDTO = reservaAlocacaoService.salvar(reservaAlocacaoCreateDTO);
        //ASSERT

        assertNotNull(reservaAlocacaoDTO);
    }

}
