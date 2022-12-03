//package br.com.allocation.service;
//
//import br.com.allocation.dto.pageDTO.PageDTO;
//import br.com.allocation.dto.vagaDTO.VagaCreateDTO;
//import br.com.allocation.dto.vagaDTO.VagaDTO;
//import br.com.allocation.entity.VagaEntity;
//import br.com.allocation.exceptions.RegraDeNegocioException;
//import br.com.allocation.repository.VagaRepository;
//import br.com.allocation.service.factory.ProgramaFactory;
//import br.com.allocation.service.factory.VagaFactory;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.test.util.ReflectionTestUtils;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.Mockito.*;
//
//@RunWith(MockitoJUnitRunner.class)
//@ExtendWith(MockitoExtension.class)
//public class VagaServiceTest {
//    @InjectMocks
//    private VagaService vagaService;
//    @Mock
//    private VagaRepository vagaRepository;
//
//    @Mock
//    private ObjectMapper objectMapper = new ObjectMapper();
//
//    @Mock
//    private ProgramaService programaService;
//
//
//    @Before
//    public void init() {
//        objectMapper.registerModule(new JavaTimeModule());
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        ReflectionTestUtils.setField(vagaService, "objectMapper", objectMapper);
//    }
//
//    @Test
//    public void deveTestarSalvarComSucesso() throws RegraDeNegocioException {
//        VagaCreateDTO vagaCreateDTO = VagaFactory.getvagaCreateDTO();
//        VagaEntity vagaEntity = VagaFactory.getVagaEntity();
//        when(programaService.findById(any())).thenReturn(ProgramaFactory.getProgramaEntity());
//
//        when(vagaRepository.save(any())).thenReturn(vagaEntity);
//        VagaDTO vagaDTO = vagaService.salvar(vagaCreateDTO);
//
//        assertNotNull(vagaDTO);
//        assertEquals("vaga", vagaCreateDTO.getNome());
//        Assertions.assertEquals("cocacolabr@mail.com.br", vagaCreateDTO.getEmailCliente());
//        Assertions.assertEquals(0, vagaDTO.getQuantidadeAlocados());
//
//    }
//
//    @Test
//    public void deveTestarListarComSucesso() {
//        Integer pagina = 4;
//        Integer quantidade = 10;
//
//        VagaEntity vagaEntity = VagaFactory.getVagaEntity();
//
//        Page<VagaEntity> vagaEntityPage = new PageImpl<>(List.of(vagaEntity));
//        when(vagaRepository.findAll(any(Pageable.class))).thenReturn(vagaEntityPage);
//        PageDTO<VagaDTO> paginaSolicitada = vagaService.listar(pagina, quantidade);
//
//        Assertions.assertNotNull(paginaSolicitada);
//        Assertions.assertEquals(quantidade, paginaSolicitada.getTamanho());
//
//    }
//
//    @Test
//    public void deveTestarListarPorId() throws RegraDeNegocioException {
//        Integer idVaga = 2;
//
//        when(vagaRepository.findById(any())).thenReturn(Optional.of(VagaFactory.getVagaEntity()));
//
//        PageDTO<VagaDTO> paginaSolicitada = vagaService.listarPorId(idVaga);
//
//        Assertions.assertNotNull(paginaSolicitada);
//    }
//
//    @Test
//    public void deveTestarEditarComSucesso() throws RegraDeNegocioException {
//        Integer id = 2;
//        VagaCreateDTO vagaCreateDTO = VagaFactory.getvagaCreateDTO();
//        VagaEntity vagaEntity = VagaFactory.getVagaEntity();
//
//        when(programaService.findById(any())).thenReturn(ProgramaFactory.getProgramaEntity());
//        when(vagaRepository.findById(anyInt())).thenReturn(Optional.of(vagaEntity));
//
//        when(vagaRepository.save(any())).thenReturn(vagaEntity);
//        VagaDTO vagaDTO = vagaService.editar(id, vagaCreateDTO);
//
//        assertNotNull(vagaDTO);
//        assertEquals("vaga", vagaCreateDTO.getNome());
//        Assertions.assertEquals("cocacolabr@mail.com.br", vagaCreateDTO.getEmailCliente());
//        Assertions.assertEquals(id, vagaDTO.getIdVaga());
//
//    }
//
//    @Test
//    public void deveTestarDeleteComSucesso() throws RegraDeNegocioException {
//        Integer id = 1;
//        VagaEntity vagaEntity = VagaFactory.getVagaEntity();
//        when(vagaRepository.findById(anyInt())).thenReturn(Optional.of(vagaEntity));
//        vagaService.deletar(id);
//        verify(vagaRepository, times(1)).delete(any());
//    }
//
//    @Test
//    public void deveTestarFindAllWithSituacaoAberto() {
//        VagaEntity vagaEntity = VagaFactory.getVagaEntity();
//        when(vagaRepository.findBySituacao(any())).thenReturn(List.of(vagaEntity));
//        List<VagaDTO> vagaDTOList = vagaService.findAllWithSituacaoAberto();
//        assertNotNull(vagaDTOList);
//    }
//
//    @Test
//    public void deveTestarFindByNomeComSucesso() throws RegraDeNegocioException {
//        String nome = "Desenvolvedor(a) Java - Back-End";
//        VagaEntity vagaEntity = VagaFactory.getVagaEntity();
//        when(vagaRepository.findByNome(any())).thenReturn(Optional.of(vagaEntity));
//
//        VagaEntity vaga = vagaService.findByNome(nome);
//
//        assertNotNull(vaga);
//        assertNotNull(vaga.getNome());
//        assertEquals(nome,vaga.getNome());
//    }
////
////    @Test(expected = RegraDeNegocioException.class)
////    public void deveTestarFindByIdComErro() throws RegraDeNegocioException {
////    }
//}
