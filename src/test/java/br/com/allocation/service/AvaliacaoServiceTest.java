package br.com.allocation.service;

import br.com.allocation.dto.avaliacaodto.AvaliacaoCreateDTO;
import br.com.allocation.dto.avaliacaodto.AvaliacaoDTO;
import br.com.allocation.dto.pagedto.PageDTO;
import br.com.allocation.entity.AlunoEntity;
import br.com.allocation.entity.AvaliacaoEntity;
import br.com.allocation.entity.VagaEntity;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.repository.AvaliacaoRepository;
import br.com.allocation.service.factory.AlunoFactory;
import br.com.allocation.service.factory.AvaliacaoFactory;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AvaliacaoServiceTest {

    @InjectMocks
    private AvaliacaoService avaliacaoService;

    @Mock
    private VagaService vagaService;
    @Mock
    private AlunoService alunoService;
    @Mock
    private AvaliacaoRepository avaliacaoRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(avaliacaoService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarSalvarComSucesso() throws RegraDeNegocioException {
        AvaliacaoEntity avaliacaoEntity = AvaliacaoFactory.getAvalicaoEntity();
        AvaliacaoCreateDTO avaliacaoCreateDTO = AvaliacaoFactory.getAvaliacaoCreateDTO();
        VagaEntity vagaEntity = VagaFactory.getVagaEntity();
        AlunoEntity alunoEntity = AlunoFactory.getAlunoEntity();

        when(vagaService.findById(anyInt())).thenReturn(vagaEntity);
        when(alunoService.findByEmail(anyString())).thenReturn(alunoEntity);
        when(avaliacaoRepository.save(any())).thenReturn(avaliacaoEntity);


        AvaliacaoDTO avaliacaoDTO = avaliacaoService.salvar(avaliacaoCreateDTO);

        assertNotNull(avaliacaoDTO);

    }

    @Test
    public void deveTestarEditarComSucesso() throws RegraDeNegocioException {
        AvaliacaoEntity avaliacaoEntity = AvaliacaoFactory.getAvalicaoEntity();
        AvaliacaoCreateDTO avaliacaoCreateDTO = AvaliacaoFactory.getAvaliacaoCreateDTO();
        Integer id = 1;
        when(avaliacaoRepository.findById(anyInt())).thenReturn(Optional.of(avaliacaoEntity));
        when(avaliacaoRepository.save(any())).thenReturn(avaliacaoEntity);

        AvaliacaoDTO avaliacaoDTO = avaliacaoService.editar(id, avaliacaoCreateDTO);

        assertNotNull(avaliacaoDTO);
    }

    @Test
    public void deveTestarDeletarComSucesso() throws RegraDeNegocioException {
        AvaliacaoEntity avaliacaoEntity = AvaliacaoFactory.getAvalicaoEntity();
        Integer id = 1;
        when(avaliacaoRepository.findById(anyInt())).thenReturn(Optional.of(avaliacaoEntity));

        avaliacaoService.deletar(id);

        verify(avaliacaoRepository, times(1)).delete(avaliacaoEntity);
    }

    @Test
    public void deveTestarListarComSucesso() {
        Integer pagina = 4;
        Integer quantidade = 6;

        AvaliacaoEntity avaliacaoEntity = AvaliacaoFactory.getAvalicaoEntity();
        Page<AvaliacaoEntity> avaliacaoEntityPage = new PageImpl<>(List.of(avaliacaoEntity));
        when(avaliacaoRepository.findAll(any(Pageable.class))).thenReturn(avaliacaoEntityPage);

        PageDTO<AvaliacaoDTO> avaliacaoDTOPageDTO = avaliacaoService.listar(pagina, quantidade);

        assertNotNull(avaliacaoDTOPageDTO);
    }

    @Test
    public void deveTestarListarPorId() throws RegraDeNegocioException {
        Integer id = 1;

        AvaliacaoEntity avaliacaoEntity = AvaliacaoFactory.getAvalicaoEntity();
        Page<AvaliacaoEntity> avaliacaoEntityPage = new PageImpl<>(List.of(avaliacaoEntity));
        when(avaliacaoRepository.findById(anyInt())).thenReturn(Optional.of(avaliacaoEntity));


        PageDTO<AvaliacaoDTO> avaliacaoDTOPageDTO = avaliacaoService.listarPorId(id);

        assertNotNull(avaliacaoDTOPageDTO);
    }

    @Test
    public void deveTestarFindByIdComSucesso() throws RegraDeNegocioException {
        AvaliacaoEntity avaliacaoEntity = AvaliacaoFactory.getAvalicaoEntity();
        Integer id = 1;
        when(avaliacaoRepository.findById(anyInt())).thenReturn(Optional.of(avaliacaoEntity));

        AvaliacaoEntity avaliacao = avaliacaoService.findById(id);

        assertNotNull(avaliacao);
        assertEquals(id, avaliacao.getIdAvaliacao());
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarFindByIdComErro() throws RegraDeNegocioException {
        Integer busca = 10;
        when(avaliacaoRepository.findById(anyInt())).thenReturn(Optional.empty());

        AvaliacaoEntity avaliacaoEntity = avaliacaoService.findById(busca);
    }

}
