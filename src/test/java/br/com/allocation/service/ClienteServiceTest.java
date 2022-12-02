package br.com.allocation.service;

import br.com.allocation.dto.clienteDTO.ClienteCreateDTO;
import br.com.allocation.dto.clienteDTO.ClienteDTO;
import br.com.allocation.dto.pageDTO.PageDTO;
import br.com.allocation.entity.ClienteEntity;
import br.com.allocation.enums.Situacao;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.repository.ClienteRepository;
import br.com.allocation.service.factory.ClienteFactory;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(clienteService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarSalvarComSucesso(){
        //SETUP
        ClienteEntity cliente = ClienteFactory.getClienteEntity();
        ClienteCreateDTO clienteCreateDTO = ClienteFactory.getClienteCreateDTO();
        when(clienteRepository.save(any())).thenReturn(cliente);
        //ACT
        ClienteDTO clienteDTO = clienteService.salvar(clienteCreateDTO);

        //ASSERT
        assertNotNull(clienteDTO);
        assertEquals("Coca Cola", clienteDTO.getNome());

    }

    @Test
    public void deveTestarListarComSucesso(){
        //SETUP
        Integer pagina = 4;
        Integer quantidade = 10;

        ClienteEntity clienteEntity = ClienteFactory.getClienteEntity();

        Page<ClienteEntity> clienteEntityPage = new PageImpl<>(List.of(clienteEntity));

        when(clienteRepository.findAll(any(Pageable.class))).thenReturn(clienteEntityPage);
        //ACT
        PageDTO<ClienteDTO> clienteDTOPageDTO = clienteService.listar(pagina, quantidade);

        //ASSERT
        assertNotNull(clienteDTOPageDTO);
    }

    @Test
    public void deveTestarEditarComSucesso() throws RegraDeNegocioException {
        //SETUP
        ClienteEntity clienteEntity = ClienteFactory.getClienteEntity();
        ClienteCreateDTO clienteCreateDTO = ClienteFactory.getClienteCreateDTO();
        Integer id = 1;
        clienteEntity.setSituacao(Situacao.FECHADO);
        when(clienteRepository.findById(anyInt())).thenReturn(Optional.of(clienteEntity));
        ClienteEntity cliente = ClienteFactory.getClienteEntity();
        when(clienteRepository.save(any())).thenReturn(cliente);

        //ACT
        ClienteDTO clienteDTO = clienteService.editar(id,clienteCreateDTO);

        //ASSERT
        assertNotNull(clienteDTO);
        assertNotEquals(Situacao.FECHADO, clienteDTO.getSituacao());
    }

    @Test
    public void deveTestarDeleteComSucesso() throws RegraDeNegocioException {
        //SETUP
        Integer id = 1;
        ClienteEntity cliente = ClienteFactory.getClienteEntity();
        when(clienteRepository.findById(anyInt())).thenReturn(Optional.of(cliente));

        //ACT
        clienteService.deletar(id);

        //ASSERT
        verify(clienteRepository, times(1)).delete(any());
    }

    @Test
    public void deveTestarFindByIdComSucesso() throws RegraDeNegocioException {
        //SETUP
        Integer id = 1;
        ClienteEntity clienteEntity = ClienteFactory.getClienteEntity();
        when(clienteRepository.findById(anyInt())).thenReturn(Optional.of(clienteEntity));
        //ACT
        ClienteEntity cliente = clienteService.findById(id);
        //ASSERT
        assertNotNull(cliente);
        assertNotNull(cliente.getIdCliente());
        assertEquals(1, cliente.getIdCliente());

    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarFindByIdComErro() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer busca = 10;
        when(clienteRepository.findById(anyInt())).thenReturn(Optional.empty());


        // Ação (ACT)
        ClienteEntity desafio = clienteService.findById(busca);

        //Assert
        assertNull(desafio);
    }

    @Test
    public void deveTestarListarPorEmailComSucesso(){
        //SETUP
        Integer pagina = 4;
        Integer quantidade = 10;
        String email = "cocacolabr@mail.com.br";

        ClienteEntity clienteEntity = ClienteFactory.getClienteEntity();

        Page<ClienteEntity> clienteEntityPage = new PageImpl<>(List.of(clienteEntity));

        when(clienteRepository.findAllByEmailIgnoreCase(any(Pageable.class), anyString())).thenReturn(clienteEntityPage);
        //ACT
        PageDTO<ClienteDTO> clienteDTOPageDTO = clienteService.listarPorEmail(pagina, quantidade, email);

        //ASSERT
        assertNotNull(clienteDTOPageDTO);
    }

    @Test
    public void deveTestarFindByEmailComSucesso() throws RegraDeNegocioException {
        //SETUP
        String email = "cocacolabr@mail.com.br";
        ClienteEntity clienteEntity = ClienteFactory.getClienteEntity();
        when(clienteRepository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.of(clienteEntity));
        //ACT
        ClienteEntity cliente = clienteService.findByEmail(email);

        //ASSERT
        assertNotNull(cliente);
    }



}
