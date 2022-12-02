package br.com.allocation.service;

import br.com.allocation.dto.alunoDTO.AlunoCreateDTO;
import br.com.allocation.dto.alunoDTO.AlunoDTO;
import br.com.allocation.dto.pageDTO.PageDTO;
import br.com.allocation.dto.reservaAlocacaoDTO.ReservaAlocacaoCreateDTO;
import br.com.allocation.dto.tecnologiaDTO.TecnologiaDTO;
import br.com.allocation.entity.AlunoEntity;
import br.com.allocation.entity.ProgramaEntity;
import br.com.allocation.entity.TecnologiaEntity;
import br.com.allocation.enums.Area;
import br.com.allocation.enums.Situacao;
import br.com.allocation.enums.StatusAluno;
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
        //ACT
        AlunoDTO alunoDTO = alunoService.salvar(alunoCreateDTO);

        //ASSERT
        assertNotNull(alunoDTO);
    }

    @Test
    public void deveTestarEditarComSucesso() throws RegraDeNegocioException {
        //SETUP
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
        //ACT
        AlunoDTO alunoDTO = alunoService.editar(id, alunoCreateDTO);

        //ASSERT
        assertNotNull(alunoDTO);
        assertNotEquals("Lazzari", alunoDTO.getNome());
    }

    @Test
    public void deveTestarListarComSucesso(){
        //SETUP
        Integer pagina = 4;
        Integer quantidade = 10;

        AlunoEntity alunoEntity = getAlunoEntity();

        Page<AlunoEntity> alunoEntities = new PageImpl<>(List.of(alunoEntity));

        when(alunoRepository.findAll(any(Pageable.class))).thenReturn(alunoEntities);
        //ACT
        PageDTO<AlunoDTO> alunoDTOPageDTO = alunoService.listar(pagina, quantidade);

        //ASSERT
        assertNotNull(alunoDTOPageDTO);
    }

    @Test
    public void deveTestarListarPorNomeComSucesso(){
        //SETUP
        String nome = "kaio";
        Integer pagina = 4;
        Integer quantidade = 10;

        AlunoEntity alunoEntity = getAlunoEntity();

        Page<AlunoEntity> alunoEntities = new PageImpl<>(List.of(alunoEntity));

        when(alunoRepository.findAllByNomeContainingIgnoreCase(any(Pageable.class), anyString())).thenReturn(alunoEntities);
        //ACT
        PageDTO<AlunoDTO> alunoDTOPageDTO = alunoService.listarPorNome(pagina, quantidade, nome);

        //ASSERT
        assertNotNull(alunoDTOPageDTO);
    }


    @Test
    public void deveTestarListarPorEmailComSucesso() throws RegraDeNegocioException {
        //SETUP
        String email = "kaio@email.com";
        Integer pagina = 4;
        Integer quantidade = 10;
        AlunoEntity alunoEntity = getAlunoEntity();
        Page<AlunoEntity> alunoEntities = new PageImpl<>(List.of(alunoEntity));

        when(alunoRepository.findAllByEmailIgnoreCase(any(Pageable.class), anyString())).thenReturn(alunoEntities);
        //ACT
        PageDTO<AlunoDTO> alunoDTOPageDTO = alunoService.listarPorEmail(pagina, quantidade, email);

        //ASSERT
        assertNotNull(alunoDTOPageDTO);
    }

    @Test
    public void deveTestarFindByIdComSucesso() throws RegraDeNegocioException {
        //SETUP
        Integer id = 1;
        AlunoEntity alunoEntity = getAlunoEntity();
        when(alunoRepository.findById(anyInt())).thenReturn(Optional.of(alunoEntity));
        //ACT
        AlunoEntity aluno = alunoService.findById(id);
        //ASSERT
        assertNotNull(aluno);
        assertNotNull(aluno.getIdAluno());
        assertEquals(1, aluno.getPrograma().getIdPrograma());
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarFindByIdComErro() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer busca = 10;
        when(alunoRepository.findById(anyInt())).thenReturn(Optional.empty());


        // Ação (ACT)
        AlunoEntity alunoEntity = alunoService.findById(busca);

        //Assert
        assertNull(alunoEntity);
    }

    @Test
    public void deveTestarDeletarComSucesso() throws RegraDeNegocioException {
        //SETUP
        Integer id = 1;
        AlunoEntity alunoEntity = getAlunoEntity();
        when(alunoRepository.findById(anyInt())).thenReturn(Optional.of(alunoEntity));
        //ACT
        alunoService.deletar(id);

        //ASSERT
        verify(alunoRepository, times(1)).deleteById(anyInt());
    }

    @Test
    public void deveTestarDisponiveisComSucesso(){
        //SETUP
        StatusAluno statusAluno = StatusAluno.DISPONIVEL;
        Integer pagina = 4;
        Integer quantidade = 10;
        AlunoEntity alunoEntity = getAlunoEntity();
        Page<AlunoEntity> alunoEntities = new PageImpl<>(List.of(alunoEntity));

        when(alunoRepository.findAllByStatusAluno(any(Pageable.class), any())).thenReturn(alunoEntities);
        //ACT
        PageDTO<AlunoDTO> alunoDTOPageDTO = alunoService.listarDisponiveis(pagina, quantidade);

        //ASSERT
        assertNotNull(alunoDTOPageDTO);
    }

    @Test
    public void deveTestarAlterarStatusAluno() throws RegraDeNegocioException {
        //SETUP
        AlunoEntity alunoEntity = getAlunoEntity();
        Integer id = 1;
        ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO = getReservaAlocacaoCreateDTO();
        when(alunoRepository.save(any())).thenReturn(alunoEntity);
        when(alunoRepository.findById(anyInt())).thenReturn(Optional.of(alunoEntity));
        //ACT
        AlunoEntity alunoEntity1 = alunoService.alterarStatusAluno(id, reservaAlocacaoCreateDTO);

        //ASSERT
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
        alunoEntity.setStatusAluno(StatusAluno.INATIVO);
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
        reservaAlocacaoCreateDTO.setStatusAluno(StatusAluno.INATIVO);
        when(alunoRepository.findById(anyInt())).thenReturn(Optional.of(alunoEntity));
        when(alunoRepository.save(any())).thenReturn(alunoEntity);

        alunoService.alterarStatusAlunoCancelado(id, reservaAlocacaoCreateDTO);

        verify(alunoRepository, times(1)).save(alunoEntity);

    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarVerificarDisponibilidadeComErroSemAluno() throws RegraDeNegocioException {
        AlunoEntity alunoEntity = getAlunoEntity();
        alunoEntity.setStatusAluno(StatusAluno.ALOCADO);
        ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO = getReservaAlocacaoCreateDTO();
        reservaAlocacaoCreateDTO.setStatusAluno(StatusAluno.ALOCADO);
        alunoService.verificarDisponibilidadeAluno(alunoEntity,reservaAlocacaoCreateDTO);
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarVerificarDisponibilidadeComErroAluno() throws RegraDeNegocioException {
        AlunoEntity alunoEntity = getAlunoEntity();
        alunoEntity.setStatusAluno(StatusAluno.RESERVADO);
        ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO = getReservaAlocacaoCreateDTO();
        reservaAlocacaoCreateDTO.setStatusAluno(StatusAluno.RESERVADO);
        alunoService.verificarDisponibilidadeAluno(alunoEntity,reservaAlocacaoCreateDTO);
    }

    private static AlunoEntity getAlunoEntity(){
        AlunoEntity alunoEntity = new AlunoEntity();
        Set<TecnologiaEntity> tecnologiaEntities = new HashSet<>();
        tecnologiaEntities.add(getTecnologiaEntity());
        alunoEntity.setIdAluno(1);
        alunoEntity.setStatusAluno(StatusAluno.DISPONIVEL);
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

    private static AlunoCreateDTO getAlunoCreateDTO(){
        AlunoCreateDTO alunoCreateDTO = new AlunoCreateDTO();
        List<String> tecnologias = new ArrayList<>();
        tecnologias.add("java");
        alunoCreateDTO.setStatusAluno(StatusAluno.DISPONIVEL);
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

    private static ProgramaEntity getProgramaEntity(){
        ProgramaEntity programaEntity = new ProgramaEntity();
        programaEntity.setIdPrograma(1);
        programaEntity.setNome("Vem ser 11");
        programaEntity.setDescricao("Vem ser vemser");
        programaEntity.setDataCriacao(LocalDate.now());
        programaEntity.setDataTermino(LocalDate.of(2023,05,01));
        programaEntity.setSituacao(Situacao.ATIVO);
        return programaEntity;
    }

    private static TecnologiaEntity getTecnologiaEntity(){
        TecnologiaEntity tecnologiaEntity = new TecnologiaEntity();
        tecnologiaEntity.setIdTecnologia(1);
        tecnologiaEntity.setNome("java");
        return tecnologiaEntity;
    }

    private static TecnologiaDTO getTecnologiaDTO(){
        TecnologiaDTO tecnologiaDTO = new TecnologiaDTO();
        tecnologiaDTO.setNome("java");
        tecnologiaDTO.setIdTecnologia(1);
        return tecnologiaDTO;
    }

    private static ReservaAlocacaoCreateDTO getReservaAlocacaoCreateDTO(){
        ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO = new ReservaAlocacaoCreateDTO();
        reservaAlocacaoCreateDTO.setIdAluno(1);
        reservaAlocacaoCreateDTO.setDescricao("Reserva da alocacao");
        reservaAlocacaoCreateDTO.setStatusAluno(StatusAluno.DISPONIVEL);
        reservaAlocacaoCreateDTO.setDataAlocacao(LocalDate.now());
        reservaAlocacaoCreateDTO.setDataReserva(LocalDate.of(2023,05,02));
        reservaAlocacaoCreateDTO.setIdVaga(1);
        reservaAlocacaoCreateDTO.setIdAvaliacao(1);
        reservaAlocacaoCreateDTO.setStatusAluno(StatusAluno.ALOCADO);
        return reservaAlocacaoCreateDTO;
    }

}
