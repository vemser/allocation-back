package br.com.allocation.service;

import br.com.allocation.dto.pagedto.PageDTO;
import br.com.allocation.dto.tecnologiadto.TecnologiaCreateDTO;
import br.com.allocation.dto.tecnologiadto.TecnologiaDTO;
import br.com.allocation.entity.TecnologiaEntity;
import br.com.allocation.repository.TecnologiaRepository;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TecnologiaServiceTest {

    @InjectMocks
    private TecnologiaService tecnologiaService;

    @Mock
    private TecnologiaRepository tecnologiaRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(tecnologiaService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarCreateComSucesso() {
        TecnologiaEntity tecnologiaEntity = getTecnologiaEntity();
        TecnologiaCreateDTO tecnologiaCreateDTO = getTecnologiaCreateDTO();
        when(tecnologiaRepository.save(any())).thenReturn(tecnologiaEntity);

        TecnologiaDTO tecnologiaDTO = tecnologiaService.create(tecnologiaCreateDTO);

        assertNotNull(tecnologiaDTO);
    }

    @Test
    public void deveTestarBuscarPorTecnologia() {
        Integer pagina = 4;
        Integer quantidade = 10;
        String nome = "java";
        PageRequest pageRequest = PageRequest.of(pagina, quantidade);

        TecnologiaEntity tecnologiaEntity = getTecnologiaEntity();

        Page<TecnologiaEntity> tecnologiaEntityPage = new PageImpl<>(List.of(tecnologiaEntity));

        when(tecnologiaRepository.findByNomeIsLikeIgnoreCase(anyString(), any(Pageable.class))).thenReturn(tecnologiaEntityPage);

        PageDTO<TecnologiaDTO> tecnologiaDTOPageDTO = tecnologiaService.buscarPorTecnologia(nome, pageRequest);


        assertNotNull(tecnologiaDTOPageDTO);
    }

    @Test
    public void deveTestarFindByName() {
        String nome = "java";
        TecnologiaEntity tecnologiaEntity = getTecnologiaEntity();
        when(tecnologiaRepository.findByNome(anyString())).thenReturn(tecnologiaEntity);

        TecnologiaDTO tecnologiaDTO = tecnologiaService.findByName(nome);

        assertNotNull(tecnologiaDTO);
        assertNotNull(tecnologiaDTO.getIdTecnologia());
        assertEquals(1, tecnologiaDTO.getIdTecnologia());
    }

    @Test
    public void deveTestarFindBySet() {
        Set<TecnologiaEntity> tecnologias = new HashSet<>();
        TecnologiaEntity tecnologiaEntity = getTecnologiaEntity();
        List<String> list = new ArrayList<>();
        list.add("java");
        tecnologias.add(tecnologiaEntity);
        when(tecnologiaRepository.findAllByNomeIn(anyList())).thenReturn(tecnologias);

        tecnologiaService.findBySet(list);

        verify(tecnologiaRepository, times(1)).findAllByNomeIn(anyList());
    }

    private static TecnologiaEntity getTecnologiaEntity() {
        TecnologiaEntity tecnologiaEntity = new TecnologiaEntity();
        tecnologiaEntity.setIdTecnologia(1);
        tecnologiaEntity.setNome("java");
        return tecnologiaEntity;
    }

    private static TecnologiaCreateDTO getTecnologiaCreateDTO() {
        TecnologiaCreateDTO tecnologiaCreateDTO = new TecnologiaCreateDTO();
        tecnologiaCreateDTO.setNome("java");
        return tecnologiaCreateDTO;
    }

}
