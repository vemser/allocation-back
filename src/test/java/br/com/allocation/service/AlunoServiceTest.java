package br.com.allocation.service;

import br.com.allocation.dto.alunodto.AlunoCreateDTO;
import br.com.allocation.dto.alunodto.AlunoDTO;
import br.com.allocation.dto.pagedto.PageDTO;
import br.com.allocation.dto.reservaAlocacaodto.ReservaAlocacaoCreateDTO;
import br.com.allocation.dto.tecnologiadto.TecnologiaDTO;
import br.com.allocation.entity.AlunoEntity;
import br.com.allocation.entity.ProgramaEntity;
import br.com.allocation.entity.TecnologiaEntity;
import br.com.allocation.enums.Area;
import br.com.allocation.enums.Situacao;
import br.com.allocation.enums.SituacaoAllocation;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.repository.AlunoRepository;
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

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AlunoServiceTest {

    @InjectMocks
    private AlunoService alunoService;

    @Mock
    private AlunoRepository alunoRepository;

    @Mock
    private TecnologiaService tecnologiaService;

    @Mock
    private ProgramaService programaService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(alunoService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarSalvarComSucesso() throws RegraDeNegocioException {
        //SETUP
        AlunoEntity alunoEntity = getAlunoEntity();
        AlunoCreateDTO alunoCreateDTO = getAlunoCreateDTO();
        TecnologiaEntity tecnologiaEntity = getTecnologiaEntity();
        TecnologiaDTO tecnologiaDTO = getTecnologiaDTO();
        Set<TecnologiaEntity> tecnologiaEntitySet = new HashSet<>();
        tecnologiaEntitySet.add(tecnologiaEntity);
        ProgramaEntity programa = getProgramaEntity();
        alunoEntity.setPrograma(programa);
        when(programaService.findById(anyInt())).thenReturn(programa);
        when(tecnologiaService.findByName(anyString())).thenReturn(null);
        when(tecnologiaService.create(any())).thenReturn(tecnologiaDTO);
        when(tecnologiaService.findBySet(any())).thenReturn(tecnologiaEntitySet);
        when(alunoRepository.save(any())).thenReturn(alunoEntity);
        AlunoDTO alunoDTO = alunoService.salvar(alunoCreateDTO);

        assertNotNull(alunoDTO);
    }

    @Test
    public void deveTestarEditarComSucesso() throws RegraDeNegocioException {
        AlunoEntity alunoEntity = getAlunoEntity();
        AlunoCreateDTO alunoCreateDTO = getAlunoCreateDTO();
        ProgramaEntity programaEntity = getProgramaEntity();
        alunoEntity.setNome("Lazzari");
        Integer id = 1;
        TecnologiaEntity tecnologiaEntity = getTecnologiaEntity();
        Set<TecnologiaEntity> tecnologiaEntitySet = new HashSet<>();
        tecnologiaEntitySet.add(tecnologiaEntity);
        AlunoEntity alunoEntityAtualizado = getAlunoEntity();
        when(programaService.findById(anyInt())).thenReturn(programaEntity);
        when(tecnologiaService.findBySet(any())).thenReturn(tecnologiaEntitySet);
        when(alunoRepository.save(any())).thenReturn(alunoEntityAtualizado);
        when(alunoRepository.findById(anyInt())).thenReturn(Optional.of(alunoEntity));

        AlunoDTO alunoDTO = alunoService.editar(id, alunoCreateDTO);

        assertNotNull(alunoDTO);
        assertNotEquals("Lazzari", alunoDTO.getNome());
    }

    @Test
    public void deveTestarListarComSucesso() {
        Integer pagina = 4;
        Integer quantidade = 10;

        AlunoEntity alunoEntity = getAlunoEntity();

        Page<AlunoEntity> alunoEntities = new PageImpl<>(List.of(alunoEntity));

        when(alunoRepository.findAll(any(Pageable.class))).thenReturn(alunoEntities);

        PageDTO<AlunoDTO> alunoDTOPageDTO = alunoService.listar(pagina, quantidade);

        assertNotNull(alunoDTOPageDTO);
    }

    @Test
    public void deveTestarListarPorNomeComSucesso() {
        String nome = "kaio";
        Integer pagina = 4;
        Integer quantidade = 10;

        AlunoEntity alunoEntity = getAlunoEntity();

        Page<AlunoEntity> alunoEntities = new PageImpl<>(List.of(alunoEntity));

        when(alunoRepository.findAllByNomeContainingIgnoreCase(any(Pageable.class), anyString())).thenReturn(alunoEntities);

        PageDTO<AlunoDTO> alunoDTOPageDTO = alunoService.listarPorNome(pagina, quantidade, nome);

        assertNotNull(alunoDTOPageDTO);
    }


    @Test
    public void deveTestarListarPorEmailComSucesso() {
        String email = "kaio@email.com";
        Integer pagina = 4;
        Integer quantidade = 10;
        AlunoEntity alunoEntity = getAlunoEntity();
        Page<AlunoEntity> alunoEntities = new PageImpl<>(List.of(alunoEntity));

        when(alunoRepository.findAllByEmailIgnoreCase(any(Pageable.class), anyString())).thenReturn(alunoEntities);

        PageDTO<AlunoDTO> alunoDTOPageDTO = alunoService.listarPorEmail(pagina, quantidade, email);

        assertNotNull(alunoDTOPageDTO);
    }

    @Test
    public void deveTestarFindByIdComSucesso() throws RegraDeNegocioException {
        Integer id = 1;
        AlunoEntity alunoEntity = getAlunoEntity();
        when(alunoRepository.findById(anyInt())).thenReturn(Optional.of(alunoEntity));

        AlunoEntity aluno = alunoService.findById(id);

        assertNotNull(aluno);
        assertNotNull(aluno.getIdAluno());
        assertEquals(1, aluno.getPrograma().getIdPrograma());
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarFindByIdComErro() throws RegraDeNegocioException {
        Integer busca = 10;
        when(alunoRepository.findById(anyInt())).thenReturn(Optional.empty());

        AlunoEntity alunoEntity = alunoService.findById(busca);
    }

    @Test
    public void deveTestarDeletarComSucesso() throws RegraDeNegocioException {
        Integer id = 1;
        AlunoEntity alunoEntity = getAlunoEntity();
        when(alunoRepository.findById(anyInt())).thenReturn(Optional.of(alunoEntity));

        alunoService.deletar(id);

        verify(alunoRepository, times(1)).deleteById(anyInt());
    }

    @Test
    public void deveTestarDisponiveisComSucesso() {
        Integer pagina = 4;
        Integer quantidade = 10;
        AlunoEntity alunoEntity = getAlunoEntity();
        Page<AlunoEntity> alunoEntities = new PageImpl<>(List.of(alunoEntity));

        when(alunoRepository.findAllBySituacaoAllocation(any(Pageable.class), any())).thenReturn(alunoEntities);

        PageDTO<AlunoDTO> alunoDTOPageDTO = alunoService.listarDisponiveis(pagina, quantidade);

        assertNotNull(alunoDTOPageDTO);
    }

    @Test
    public void deveTestarAlterarStatusAluno() throws RegraDeNegocioException {
        AlunoEntity alunoEntity = getAlunoEntity();
        Integer id = 1;
        ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO = getReservaAlocacaoCreateDTO();
        when(alunoRepository.save(any())).thenReturn(alunoEntity);
        when(alunoRepository.findById(anyInt())).thenReturn(Optional.of(alunoEntity));

        AlunoEntity alunoEntity1 = alunoService.alterarStatusAluno(id, reservaAlocacaoCreateDTO);

        assertNotNull(alunoEntity1);
    }

    @Test
    public void deveTestarFindByEmailComSucesso() throws RegraDeNegocioException {
        AlunoEntity alunoEntity = getAlunoEntity();
        String email = "kaio@email.com";
        when(alunoRepository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.of(alunoEntity));

        alunoEntity = alunoService.findByEmail(email);

        assertNotNull(alunoEntity);
    }

    @Test
    public void deveTestarAlterarStatusAlunoCancelado() throws RegraDeNegocioException {
        Integer id = 1;
        ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO = getReservaAlocacaoCreateDTO();
        AlunoEntity alunoEntity = getAlunoEntity();
        alunoEntity.setSituacaoAllocation(SituacaoAllocation.FINALIZADO);
        when(alunoRepository.findById(anyInt())).thenReturn(Optional.of(alunoEntity));
        when(alunoRepository.save(any())).thenReturn(alunoEntity);

        alunoService.alterarStatusAlunoCancelado(id, reservaAlocacaoCreateDTO);

        verify(alunoRepository, times(1)).save(alunoEntity);

    }

    @Test
    public void deveTestarAlterarStatusAlunoCanceladoInativo() throws RegraDeNegocioException {
        Integer id = 1;
        ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO = getReservaAlocacaoCreateDTO();
        AlunoEntity alunoEntity = getAlunoEntity();
        reservaAlocacaoCreateDTO.setSituacaoAllocation(SituacaoAllocation.FINALIZADO);
        when(alunoRepository.findById(anyInt())).thenReturn(Optional.of(alunoEntity));
        when(alunoRepository.save(any())).thenReturn(alunoEntity);

        alunoService.alterarStatusAlunoCancelado(id, reservaAlocacaoCreateDTO);

        verify(alunoRepository, times(1)).save(alunoEntity);

    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarVerificarDisponibilidadeComErroSemAluno() throws RegraDeNegocioException {
        AlunoEntity alunoEntity = getAlunoEntity();
        alunoEntity.setSituacaoAllocation(SituacaoAllocation.ALOCADO);
        ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO = getReservaAlocacaoCreateDTO();
        reservaAlocacaoCreateDTO.setSituacaoAllocation(SituacaoAllocation.ALOCADO);
        alunoService.verificarDisponibilidadeAluno(alunoEntity, reservaAlocacaoCreateDTO);
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarVerificarDisponibilidadeComErroAluno() throws RegraDeNegocioException {
        AlunoEntity alunoEntity = getAlunoEntity();
        alunoEntity.setSituacaoAllocation(SituacaoAllocation.RESERVADO);
        ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO = getReservaAlocacaoCreateDTO();
        reservaAlocacaoCreateDTO.setSituacaoAllocation(SituacaoAllocation.RESERVADO);
        alunoService.verificarDisponibilidadeAluno(alunoEntity, reservaAlocacaoCreateDTO);
    }

    private static AlunoEntity getAlunoEntity() {
        AlunoEntity alunoEntity = new AlunoEntity();
        Set<TecnologiaEntity> tecnologiaEntities = new HashSet<>();
        tecnologiaEntities.add(getTecnologiaEntity());
        alunoEntity.setIdAluno(1);
        alunoEntity.setSituacaoAllocation(SituacaoAllocation.DISPONIVEL);
        alunoEntity.setNome("Kaio");
        alunoEntity.setEmail("kaio@email.com");
        alunoEntity.setArea(Area.BACKEND);
        alunoEntity.setCidade("Capela");
        alunoEntity.setEstado("Sergipe");
        alunoEntity.setDescricao("Bom aluno");
        alunoEntity.setTelefone("1234");
        alunoEntity.setPrograma(getProgramaEntity());
        alunoEntity.setTecnologias(tecnologiaEntities);
        return alunoEntity;
    }

    private static AlunoCreateDTO getAlunoCreateDTO() {
        AlunoCreateDTO alunoCreateDTO = new AlunoCreateDTO();
        List<String> tecnologias = new ArrayList<>();
        tecnologias.add("java");
        alunoCreateDTO.setSituacao(SituacaoAllocation.DISPONIVEL);
        alunoCreateDTO.setNome("Kaio");
        alunoCreateDTO.setEmail("kaio@email.com");
        alunoCreateDTO.setArea(Area.BACKEND);
        alunoCreateDTO.setCidade("Capela");
        alunoCreateDTO.setEstado("Sergipe");
        alunoCreateDTO.setDescricao("Bom aluno");
        alunoCreateDTO.setTelefone("1234");
        alunoCreateDTO.setIdPrograma(1);
        alunoCreateDTO.setTecnologias(tecnologias);
        return alunoCreateDTO;
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

    private static TecnologiaEntity getTecnologiaEntity() {
        TecnologiaEntity tecnologiaEntity = new TecnologiaEntity();
        tecnologiaEntity.setIdTecnologia(1);
        tecnologiaEntity.setNome("java");
        return tecnologiaEntity;
    }

    private static TecnologiaDTO getTecnologiaDTO() {
        TecnologiaDTO tecnologiaDTO = new TecnologiaDTO();
        tecnologiaDTO.setNome("java");
        tecnologiaDTO.setIdTecnologia(1);
        return tecnologiaDTO;
    }

    private static ReservaAlocacaoCreateDTO getReservaAlocacaoCreateDTO() {
        ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO = new ReservaAlocacaoCreateDTO();
        reservaAlocacaoCreateDTO.setIdAluno(1);
        reservaAlocacaoCreateDTO.setDescricao("Reserva da alocacao");
        reservaAlocacaoCreateDTO.setSituacaoAllocation(SituacaoAllocation.DISPONIVEL);
        reservaAlocacaoCreateDTO.setDataAlocacao(LocalDate.now());
        reservaAlocacaoCreateDTO.setDataReserva(LocalDate.of(2023, 05, 02));
        reservaAlocacaoCreateDTO.setIdVaga(1);
        reservaAlocacaoCreateDTO.setIdAvaliacao(1);
        reservaAlocacaoCreateDTO.setSituacaoAllocation(SituacaoAllocation.ALOCADO);
        return reservaAlocacaoCreateDTO;
    }

}
