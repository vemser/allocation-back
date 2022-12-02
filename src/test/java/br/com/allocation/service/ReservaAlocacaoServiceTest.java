package br.com.allocation.service;

import br.com.allocation.dto.clienteDTO.ClienteDTO;
import br.com.allocation.dto.pageDTO.PageDTO;
import br.com.allocation.dto.reservaAlocacaoDTO.ReservaAlocacaoCreateDTO;
import br.com.allocation.dto.reservaAlocacaoDTO.ReservaAlocacaoDTO;
import br.com.allocation.entity.AlunoEntity;
import br.com.allocation.entity.ClienteEntity;
import br.com.allocation.entity.ReservaAlocacaoEntity;
import br.com.allocation.entity.VagaEntity;
import br.com.allocation.enums.StatusAluno;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.repository.AlunoRepository;
import br.com.allocation.repository.ReservaAlocacaoRepository;
import br.com.allocation.service.factory.AlunoFactory;
import br.com.allocation.service.factory.ClienteFactory;
import br.com.allocation.service.factory.ReservaAlocacaoFactory;
import br.com.allocation.service.factory.VagaFactory;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarSalvarComErro() throws RegraDeNegocioException {
        //SETUP
        ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO = ReservaAlocacaoFactory.getReservaAlocacaoCreateDTO();
        ReservaAlocacaoEntity reservaAlocacaoEntity = ReservaAlocacaoFactory.getReservaAlocacaoEntity();
        AlunoEntity aluno = AlunoFactory.getAlunoEntity();
        when(alunoService.alterarStatusAluno(any(), any())).thenReturn(aluno);
        when(alunoService.findById(anyInt())).thenReturn(aluno);
        //ACT
        doThrow(DataIntegrityViolationException.class).when(reservaAlocacaoRepository).save(any());
        ReservaAlocacaoDTO reservaAlocacaoDTO = reservaAlocacaoService.salvar(reservaAlocacaoCreateDTO);

        //ASSERT
        verify(reservaAlocacaoRepository, times(1)).save(any());
    }

    @Test
    public void deveTestarEditarComSucesso() throws RegraDeNegocioException {
        Integer codigo = 1;
        ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO = ReservaAlocacaoFactory.getReservaAlocacaoCreateDTO();
        ReservaAlocacaoEntity reservaAlocacaoEntity = ReservaAlocacaoFactory.getReservaAlocacaoEntity();
        AlunoEntity aluno = AlunoFactory.getAlunoEntity();
        aluno.setStatusAluno(StatusAluno.ALOCADO);
        reservaAlocacaoCreateDTO.setStatusAluno(StatusAluno.ALOCADO);
        reservaAlocacaoEntity.setAluno(aluno);
        when(alunoService.findById(anyInt())).thenReturn(aluno);
        when(reservaAlocacaoRepository.findById(anyInt())).thenReturn(Optional.of(reservaAlocacaoEntity));
        ReservaAlocacaoEntity reservaAlocacao = ReservaAlocacaoFactory.getReservaAlocacaoEntity();
        reservaAlocacao.setDescricao("abc");
        when(reservaAlocacaoRepository.save(any())).thenReturn(reservaAlocacao);

        ReservaAlocacaoDTO reservaAlocacaoDTO = reservaAlocacaoService.editar(codigo, reservaAlocacaoCreateDTO);

        assertNotNull(reservaAlocacao);
        assertNotEquals(reservaAlocacaoEntity.getDescricao(), reservaAlocacaoDTO.getDescricao());
    }

    @Test
    public void deveTestarEditarComSucessoReservado() throws RegraDeNegocioException {
        Integer codigo = 1;
        ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO = ReservaAlocacaoFactory.getReservaAlocacaoCreateDTO();
        ReservaAlocacaoEntity reservaAlocacaoEntity = ReservaAlocacaoFactory.getReservaAlocacaoEntity();
        AlunoEntity aluno = AlunoFactory.getAlunoEntity();
        aluno.setStatusAluno(StatusAluno.ALOCADO);
        VagaEntity vagaEntity = VagaFactory.getVagaEntity();
        reservaAlocacaoEntity.setVaga(vagaEntity);
        reservaAlocacaoCreateDTO.setStatusAluno(StatusAluno.RESERVADO);
        reservaAlocacaoCreateDTO.setIdVaga(2);
        reservaAlocacaoEntity.setAluno(aluno);
        when(alunoService.findById(anyInt())).thenReturn(aluno);
        when(vagaService.findById(anyInt())).thenReturn(vagaEntity);
        when(reservaAlocacaoRepository.findById(anyInt())).thenReturn(Optional.of(reservaAlocacaoEntity));
        ReservaAlocacaoEntity reservaAlocacao = ReservaAlocacaoFactory.getReservaAlocacaoEntity();
        reservaAlocacao.setDescricao("abc");
        when(reservaAlocacaoRepository.save(any())).thenReturn(reservaAlocacao);

        ReservaAlocacaoDTO reservaAlocacaoDTO = reservaAlocacaoService.editar(codigo, reservaAlocacaoCreateDTO);

        assertNotNull(reservaAlocacao);
        assertNotEquals(reservaAlocacaoEntity.getDescricao(), reservaAlocacaoDTO.getDescricao());
    }

    @Test
    public void deveTestarDeletarComSucesso() throws RegraDeNegocioException {
        Integer id = 1;
        ReservaAlocacaoEntity reservaAlocacaoEntity = ReservaAlocacaoFactory.getReservaAlocacaoEntity();
        AlunoEntity aluno = AlunoFactory.getAlunoEntity();
        when(reservaAlocacaoRepository.findById(anyInt())).thenReturn(Optional.of(reservaAlocacaoEntity));

        reservaAlocacaoService.deletar(id);

        verify(reservaAlocacaoRepository, times(1)).delete(reservaAlocacaoEntity);

    }

    @Test
    public void deveTestarListarComSucesso(){
        //SETUP
        Integer pagina = 4;
        Integer quantidade = 10;

        ReservaAlocacaoEntity reservaAlocacaoEntity = ReservaAlocacaoFactory.getReservaAlocacaoEntity();

        Page<ReservaAlocacaoEntity> reservaAlocacaoEntityPage = new PageImpl<>(List.of(reservaAlocacaoEntity));

        when(reservaAlocacaoRepository.findAll(any(Pageable.class))).thenReturn(reservaAlocacaoEntityPage);
        //ACT
        PageDTO<ReservaAlocacaoDTO> reservaAlocacaoDTOPageDTO = reservaAlocacaoService.listar(pagina, quantidade);

        //ASSERT
        assertNotNull(reservaAlocacaoDTOPageDTO);
    }

}
