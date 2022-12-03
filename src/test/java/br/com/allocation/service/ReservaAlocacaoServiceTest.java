package br.com.allocation.service;

import br.com.allocation.dto.pageDTO.PageDTO;
import br.com.allocation.dto.reservaAlocacaoDTO.ReservaAlocacaoCreateDTO;
import br.com.allocation.dto.reservaAlocacaoDTO.ReservaAlocacaoDTO;
import br.com.allocation.entity.AlunoEntity;
import br.com.allocation.entity.ReservaAlocacaoEntity;
import br.com.allocation.entity.VagaEntity;
import br.com.allocation.enums.StatusAluno;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.repository.AlunoRepository;
import br.com.allocation.repository.ReservaAlocacaoRepository;
import br.com.allocation.service.factory.AlunoFactory;
import br.com.allocation.service.factory.ReservaAlocacaoFactory;
import br.com.allocation.service.factory.VagaFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

        ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO = ReservaAlocacaoFactory.getReservaAlocacaoCreateDTO();
        ReservaAlocacaoEntity reservaAlocacaoEntity = ReservaAlocacaoFactory.getReservaAlocacaoEntity();
        AlunoEntity aluno = AlunoFactory.getAlunoEntity();
        Set<ReservaAlocacaoEntity> reservaAlocacaoEntitySet = new HashSet<>();
        reservaAlocacaoEntitySet.add(reservaAlocacaoEntity);
        aluno.setReservaAlocacaos(reservaAlocacaoEntitySet);
        reservaAlocacaoCreateDTO.setStatusAluno(StatusAluno.DISPONIVEL);

        when(reservaAlocacaoRepository.save(any())).thenReturn(reservaAlocacaoEntity);
        when(alunoService.findById(anyInt())).thenReturn(aluno);

        ReservaAlocacaoDTO reservaAlocacaoDTO = reservaAlocacaoService.salvar(reservaAlocacaoCreateDTO);


        assertNotNull(reservaAlocacaoDTO);
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarSalvarComErro() throws RegraDeNegocioException {
        ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO = ReservaAlocacaoFactory.getReservaAlocacaoCreateDTO();
        ReservaAlocacaoEntity reservaAlocacaoEntity = ReservaAlocacaoFactory.getReservaAlocacaoEntity();
        AlunoEntity aluno = AlunoFactory.getAlunoEntity();
        VagaEntity vaga = VagaFactory.getVagaEntity();
        reservaAlocacaoCreateDTO.setStatusAluno(StatusAluno.DISPONIVEL);
        when(alunoService.findById(anyInt())).thenReturn(aluno);

        doThrow(DataIntegrityViolationException.class).when(reservaAlocacaoRepository).save(any());
        reservaAlocacaoService.salvar(reservaAlocacaoCreateDTO);

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
        VagaEntity vaga = VagaFactory.getVagaEntity();
        reservaAlocacaoEntity.setVaga(vaga);
        Set<ReservaAlocacaoEntity> reservaAlocacaoEntitySet = new HashSet<>();
        reservaAlocacaoEntitySet.add(reservaAlocacaoEntity);
        aluno.setReservaAlocacaos(reservaAlocacaoEntitySet);
        when(alunoService.findById(anyInt())).thenReturn(aluno);
        when(reservaAlocacaoRepository.findById(anyInt())).thenReturn(Optional.of(reservaAlocacaoEntity));
        ReservaAlocacaoEntity reservaAlocacao = ReservaAlocacaoFactory.getReservaAlocacaoEntity();
        reservaAlocacao.setDescricao("abc");
        when(reservaAlocacaoRepository.save(any())).thenReturn(reservaAlocacao);
        when(vagaService.findById(anyInt())).thenReturn(vaga);
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
        Set<ReservaAlocacaoEntity> reservaAlocacaoEntitySet = new HashSet<>();
        reservaAlocacaoEntitySet.add(reservaAlocacaoEntity);
        aluno.setReservaAlocacaos(reservaAlocacaoEntitySet);
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


    }

    @Test
    public void deveTestarListarComSucesso() {
        Integer pagina = 4;
        Integer quantidade = 10;

        ReservaAlocacaoEntity reservaAlocacaoEntity = ReservaAlocacaoFactory.getReservaAlocacaoEntity();

        Page<ReservaAlocacaoEntity> reservaAlocacaoEntityPage = new PageImpl<>(List.of(reservaAlocacaoEntity));

        when(reservaAlocacaoRepository.findAll(any(Pageable.class))).thenReturn(reservaAlocacaoEntityPage);

        PageDTO<ReservaAlocacaoDTO> reservaAlocacaoDTOPageDTO = reservaAlocacaoService.listar(pagina, quantidade);


        assertNotNull(reservaAlocacaoDTOPageDTO);
    }

}
