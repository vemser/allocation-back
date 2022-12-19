package br.com.allocation.service;

import br.com.allocation.dto.pageDTO.PageDTO;
import br.com.allocation.dto.programaDTO.ProgramaCreateDTO;
import br.com.allocation.dto.programaDTO.ProgramaDTO;
import br.com.allocation.entity.ProgramaEntity;
import br.com.allocation.enums.Situacao;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.repository.ProgramaRepository;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProgramaServiceTest {

    @InjectMocks
    private ProgramaService programaService;

    @Mock
    private ProgramaRepository programaRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(programaService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarSalvarComSucesso() {
        ProgramaEntity programaEntity = getProgramaEntity();
        ProgramaCreateDTO programaCreateDTO = getProgramaCreateDTO();
        when(programaRepository.save(any())).thenReturn(programaEntity);

        ProgramaDTO programaDTO = programaService.salvar(programaCreateDTO);


        assertNotNull(programaDTO);
        assertNotNull(programaDTO.getIdPrograma());
        assertEquals(1, programaDTO.getIdPrograma());
    }

    @Test
    public void deveTestarListarComSucesso() {
        Integer pagina = 4;
        Integer quantidade = 10;

        ProgramaEntity programa = getProgramaEntity();

        Page<ProgramaEntity> programaEntityPage = new PageImpl<>(List.of(programa));

        when(programaRepository.findAll(any(Pageable.class))).thenReturn(programaEntityPage);

        PageDTO<ProgramaDTO> programaDTOPageDTO = programaService.listar(pagina, quantidade);

        assertNotNull(programaDTOPageDTO);
    }

    @Test
    public void deveTestarEditarComSucesso() throws RegraDeNegocioException {
        Integer id = 1;
        ProgramaCreateDTO programaCreateDTO = getProgramaCreateDTO();
        ProgramaEntity programaEntity = getProgramaEntity();
        programaEntity.setSituacao(Situacao.FECHADO);
        when(programaRepository.findById(anyInt())).thenReturn(Optional.of(programaEntity));
        ProgramaEntity programaEntity1 = getProgramaEntity();
        when(programaRepository.save(any())).thenReturn(programaEntity1);

        ProgramaDTO programaDTO = programaService.editar(id, programaCreateDTO);

        assertNotNull(programaDTO);
        assertNotEquals(Situacao.FECHADO, programaDTO.getSituacao());

    }

    @Test
    public void deveTestarDeleteComSucesso() throws RegraDeNegocioException {
        Integer id = 1;
        ProgramaEntity programaEntity = getProgramaEntity();
        when(programaRepository.findById(anyInt())).thenReturn(Optional.of(programaEntity));

        programaService.deletar(id);

        verify(programaRepository, times(1)).delete(any());

    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarDeleteComErro() throws RegraDeNegocioException {
        Integer id = 2;
        ProgramaEntity programaEntity = getProgramaEntity();
        when(programaRepository.findById(anyInt())).thenReturn(Optional.of(programaEntity));

        doThrow(DataIntegrityViolationException.class).when(programaRepository).delete(any());
        programaService.deletar(id);

        verify(programaRepository, times(1)).delete(any());

    }

    @Test
    public void deveTestarListarPorNomeComSucesso() {
        Integer pagina = 4;
        Integer quantidade = 10;
        String nome = "Vem ser 11";

        ProgramaEntity programa = getProgramaEntity();

        Page<ProgramaEntity> programaEntityPage = new PageImpl<>(List.of(programa));

        when(programaRepository.findAllByNomeContainingIgnoreCase(anyString(), any(Pageable.class))).thenReturn(programaEntityPage);

        PageDTO<ProgramaDTO> programaDTOPageDTO = programaService.listarPorNome(pagina, quantidade, nome);

        assertNotNull(programaDTOPageDTO);
    }

    @Test
    public void deveTestarFindByIdComSucesso() throws RegraDeNegocioException {
        Integer id = 1;
        ProgramaEntity programaEntity = getProgramaEntity();
        when(programaRepository.findById(anyInt())).thenReturn(Optional.of(programaEntity));

        ProgramaEntity programa = programaService.findById(id);

        assertNotNull(programa);
        assertNotNull(programa.getIdPrograma());
        assertEquals(1, programa.getIdPrograma());

    }

    @Test
    public void deveTestarListarPorIdComSucesso() throws RegraDeNegocioException {
        Integer id = 1;
        ProgramaEntity programaEntity = getProgramaEntity();
        when(programaRepository.findById(anyInt())).thenReturn(Optional.of(programaEntity));

        PageDTO<ProgramaDTO> programaDTO = programaService.listarPorId(id);

        assertNotNull(programaDTO);

    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarFindByIdComErro() throws RegraDeNegocioException {
        Integer busca = 10;
        when(programaRepository.findById(anyInt())).thenReturn(Optional.empty());

        ProgramaEntity programa = programaService.findById(busca);
        // FIXME não precisa do assert
        assertNull(programa);
    }

    @Test
    public void deveTestarFindByNomeComSucesso() throws RegraDeNegocioException {
        String nome = "Vem ser 11";
        ProgramaEntity programaEntity = getProgramaEntity();
        when(programaRepository.findByNomeContainingIgnoreCase(anyString())).thenReturn(Optional.of(programaEntity));

        ProgramaEntity programa = programaService.findByNome(nome);

        assertNotNull(programa);
        assertNotNull(programa.getIdPrograma());
        assertEquals(1, programa.getIdPrograma());

    }


    private static ProgramaEntity getProgramaEntity() {
        ProgramaEntity programaEntity = new ProgramaEntity();
        programaEntity.setIdPrograma(1);
        programaEntity.setNome("Vem ser 11");
        programaEntity.setDescricao("Vem ser vemser");
        programaEntity.setDataCriacao(LocalDate.now());
        programaEntity.setDataTermino(LocalDate.of(2023, 05, 01));
        programaEntity.setSituacao(Situacao.ATIVO);
        return programaEntity;
    }



    public static ProgramaCreateDTO getProgramaCreateDTO() {
        ProgramaCreateDTO programaCreateDTO = new ProgramaCreateDTO();
        programaCreateDTO.setDataCriacao(LocalDate.now());
        programaCreateDTO.setDataTermino(LocalDate.of(2023, 05, 01));
        programaCreateDTO.setNome("Vem ser 11");
        programaCreateDTO.setSituacao(String.valueOf(Situacao.ATIVO));
        programaCreateDTO.setDescricao("Vem ser vemser");
        return programaCreateDTO;
    }

}
