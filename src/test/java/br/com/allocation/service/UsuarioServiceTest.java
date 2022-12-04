package br.com.allocation.service;

import br.com.allocation.dto.cargoDTO.CargoDTO;
import br.com.allocation.dto.loginDTO.LoginWithIdDTO;
import br.com.allocation.dto.pageDTO.PageDTO;
import br.com.allocation.dto.usuarioDTO.UsuarioCargosDTO;
import br.com.allocation.dto.usuarioDTO.UsuarioCreateDTO;
import br.com.allocation.dto.usuarioDTO.UsuarioDTO;
import br.com.allocation.dto.usuarioDTO.UsuarioSenhaDTO;
import br.com.allocation.entity.CargoEntity;
import br.com.allocation.entity.UsuarioEntity;
import br.com.allocation.enums.Cargos;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.repository.UsuarioRepository;
import br.com.allocation.security.TokenService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    private ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private CargoService cargoService;

    @Mock
    private TokenService tokenService;

    @Mock
    private EmailService emailService;

    @Mock
    private SecurityContextHolder securityContextHolder;


    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(usuarioService, "objectMapper", objectMapper);
    }


    @Test
    public void deveTestarAdicionarUsuarioComSucesso() throws RegraDeNegocioException {
        UsuarioCreateDTO usuarioCreateDTO = getUsuarioCreateDTO();
        Cargos cargos = Cargos.ADMINISTRADOR;

        UsuarioEntity usuarioEntity = getUsuarioEntity();
        usuarioEntity.setIdUsuario(10);
        when(cargoService.findByNome(anyString())).thenReturn(getCargoEntity());
        String senha = "uiq@123456";
        when(passwordEncoder.encode(senha)).thenReturn("Ahu82ha");
        when(usuarioRepository.save(any())).thenReturn(usuarioEntity);

        UsuarioDTO usuarioDTO = usuarioService.create(usuarioCreateDTO, cargos);

        assertNotNull(usuarioDTO);
        assertEquals("gustavo lucena silva", usuarioDTO.getNomeCompleto());
        assertEquals(10, usuarioDTO.getIdUsuario());
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarAdicionarUsuarioComEmailInvalido() throws RegraDeNegocioException {
        UsuarioCreateDTO usuarioCreateDTO = getUsuarioCreateDTO();
        usuarioCreateDTO.setSenhaIgual("123qwetryy");
        Cargos cargos = Cargos.ADMINISTRADOR;

        when(usuarioRepository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.of(getUsuarioEntity()));

        UsuarioDTO usuarioDTO = usuarioService.create(usuarioCreateDTO, cargos);
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarAdicionarUsuarioComSenhasDiferentes() throws RegraDeNegocioException {

        UsuarioCreateDTO usuarioCreateDTO = getUsuarioCreateDTO();
        usuarioCreateDTO.setSenhaIgual("123qwetryy");
        Cargos cargos = Cargos.ADMINISTRADOR;

        UsuarioDTO usuarioDTO = usuarioService.create(usuarioCreateDTO, cargos);
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarAdicionarUsuarioComSenhaPequena() throws RegraDeNegocioException {
        UsuarioCreateDTO usuarioCreateDTO = getUsuarioCreateDTO();
        usuarioCreateDTO.setSenha("123");
        usuarioCreateDTO.setSenhaIgual("123");
        Cargos cargos = Cargos.ADMINISTRADOR;

        UsuarioDTO usuarioDTO = usuarioService.create(usuarioCreateDTO, cargos);
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarAdicionarUsuarioComSenhaFraca() throws RegraDeNegocioException {
        UsuarioCreateDTO usuarioCreateDTO = getUsuarioCreateDTO();
        usuarioCreateDTO.setSenha("1234567");
        usuarioCreateDTO.setSenhaIgual("1237897");
        Cargos cargos = Cargos.ADMINISTRADOR;

        UsuarioDTO usuarioDTO = usuarioService.create(usuarioCreateDTO, cargos);
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarAdicionarUsuarioComSenhaFracaMenor() throws RegraDeNegocioException {
        UsuarioCreateDTO usuarioCreateDTO = getUsuarioCreateDTO();
        usuarioCreateDTO.setSenha("aaaaaaa1");
        usuarioCreateDTO.setSenhaIgual("aaaaaaa1");
        Cargos cargos = Cargos.ADMINISTRADOR;

        UsuarioDTO usuarioDTO = usuarioService.create(usuarioCreateDTO, cargos);
    }

    @Test
    public void deveTestarEditarUsuarioComSucesso() throws RegraDeNegocioException {
        Integer id = 10;
        UsuarioCreateDTO usuarioCreateDTO = getUsuarioCreateDTO();
        Cargos cargos = Cargos.ADMINISTRADOR;
        UsernamePasswordAuthenticationToken dto = new UsernamePasswordAuthenticationToken(1, null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(dto);

        UsuarioEntity usuarioEntity = getUsuarioEntity();
        UsuarioEntity usuario = getUsuarioEntity();
        usuario.setSenha("48jdye12");
        usuario.setNomeCompleto("gustavo l");

        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuarioEntity));
        when(passwordEncoder.encode(anyString())).thenReturn("Ahu82ha");
        when(usuarioRepository.save(any())).thenReturn(usuario);
        when(cargoService.findByNome(anyString())).thenReturn(getCargoEntity());

        UsuarioDTO usuarioDTO = usuarioService.editar(id, usuarioCreateDTO, cargos);

        assertNotNull(usuarioDTO);
        assertEquals(10, usuarioDTO.getIdUsuario());
        assertNotEquals(usuarioDTO.getNomeCompleto(), usuarioEntity.getNomeCompleto());
    }

    @Test
    public void deveListarUsuariosPaginadoComSucesso() {
        Integer pagina = 4;
        Integer quantidade = 6;

        UsuarioEntity usuarioEntity = getUsuarioEntity();
        Page<UsuarioEntity> usuarioEntityPage = new PageImpl<>(List.of(usuarioEntity));
        when(usuarioRepository.findAll(any(Pageable.class))).thenReturn(usuarioEntityPage);

        PageDTO<UsuarioDTO> usuarioEntityPage1 = usuarioService.listar(pagina, quantidade);

        assertNotNull(usuarioEntityPage1);
    }

    @Test
    public void deveTestarListarPorEmailPagComSucesso() throws RegraDeNegocioException {
        Integer pagina = 4;
        Integer quantidade = 6;
        String email = "gustavo@dbccompany.com.br";

        UsuarioEntity usuarioEntity = getUsuarioEntity();
        Page<UsuarioEntity> usuarioEntityPage = new PageImpl<>(List.of(usuarioEntity));
        when(usuarioRepository.findAllByEmail(any(Pageable.class), anyString())).thenReturn(usuarioEntityPage);

        PageDTO<UsuarioDTO> usuarioEntityPage1 = usuarioService.listarPorEmailPag(pagina, quantidade, email);

        assertNotNull(usuarioEntityPage1);
    }

    @Test
    public void deveListarUsuariosPorNomeComSucesso() {
        Integer pagina = 4;
        Integer quantidade = 6;
        String nome = "gustavo";

        UsuarioEntity usuarioEntity = getUsuarioEntity();
        Page<UsuarioEntity> usuarioEntityPage = new PageImpl<>(List.of(usuarioEntity));
        when(usuarioRepository.findAllByNomeCompletoContainingIgnoreCase(any(Pageable.class), anyString())).thenReturn(usuarioEntityPage);

        PageDTO<UsuarioDTO> usuarioEntityPage1 = usuarioService.listarPorNome(pagina, quantidade, nome);

        assertNotNull(usuarioEntityPage1);
    }

    @Test
    public void deveListarUsuariosPorCargoComSucesso() throws RegraDeNegocioException {
        Integer pagina = 4;
        Integer quantidade = 6;
        Cargos cargo = Cargos.GESTOR;

        UsuarioEntity usuarioEntity = getUsuarioEntity();
        Page<UsuarioEntity> usuarioEntityPage = new PageImpl<>(List.of(usuarioEntity));
        when(usuarioRepository.findAllByCargosContainingIgnoreCase(any(Pageable.class), any())).thenReturn(usuarioEntityPage);

        PageDTO<UsuarioDTO> usuarioEntities = usuarioService.listarPorCargo(pagina, quantidade, cargo);

        assertNotNull(usuarioEntities);
    }

    @Test
    public void deveFindUsuarioDTObyEmailComSucesso() throws RegraDeNegocioException {
        String email = "gustavo@dbccompany.com.br";
        when(usuarioRepository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.of(getUsuarioEntity()));

        UsuarioDTO usuarioDTO = usuarioService.findUsuarioDTObyEmail(email);

        assertNotNull(usuarioDTO);
        assertEquals(10, usuarioDTO.getIdUsuario());
        assertEquals("gustavo@dbccompany.com.br", usuarioDTO.getEmail());
    }

    @Test
    public void deveTestarDeletarUsuarioComSucesso() throws RegraDeNegocioException {
        Integer id = 10;

        UsuarioEntity usuarioEntity = getUsuarioEntity();
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuarioEntity));
        usuarioService.deletar(id);

        verify(usuarioRepository, times(1)).delete(any());

    }

    @Test
    public void deveTestarAtualizarCargo() throws RegraDeNegocioException {
        Integer id = 10;
        String email = "gustavo@dbccompany.com.br";
        UsuarioEntity usuarioEntity = getUsuarioEntity();

        UsuarioDTO usuarioDTO = getUsuarioDTO();

        UsuarioCargosDTO usuarioCargosDTO = new UsuarioCargosDTO();
        usuarioCargosDTO.setEmailUsuario(email);
        usuarioCargosDTO.setCargo(usuarioDTO.getCargo());


        when(usuarioRepository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.of(usuarioEntity));
        when(usuarioRepository.save(any())).thenReturn(getUsuarioEntity());

        UsuarioDTO cargosDTO = usuarioService.atualizarCargo(usuarioCargosDTO);

        assertNotNull(cargosDTO);
        assertNotEquals("ROLE_ADMINISTRADOR", usuarioDTO.getCargo().getNome());
    }

    @Test
    public void deveTestarGetLoggedUser() throws RegraDeNegocioException {
        UsernamePasswordAuthenticationToken dto = new UsernamePasswordAuthenticationToken(1, null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(dto);
        UsuarioEntity usuarioEntity = getUsuarioEntity();

        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuarioEntity));

        LoginWithIdDTO loginWithIdDTO = usuarioService.getLoggedUser();

        assertNotNull(loginWithIdDTO);
        assertEquals(usuarioEntity.getIdUsuario(), loginWithIdDTO.getIdUsuario());
    }

    @Test
    public void deveTestarRecuperarSenhaComSucesso() throws RegraDeNegocioException {
        String email = "gustavo@dbccompany.com.br";
        UsuarioEntity usuarioEntity = getUsuarioEntity();
        String token = "12312412412dqwdqwdq12";
        when(tokenService.getTokenRecuperarSenha(any())).thenReturn(token);
        when(usuarioRepository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.of(usuarioEntity));

        String recuperar = usuarioService.recuperarSenha(email);

        assertNotNull(recuperar);

    }

    @Test
    public void deveTertarAtualizarSenhaComSucesso() throws RegraDeNegocioException {
        String senha = "123456qp";
        String senhaIgual = "123456qp";
        String token = "vksdbvisbvçdmd54v448d4v5e48v4efe5f8fw1";
        UsuarioSenhaDTO usuarioSenhaDTO = new UsuarioSenhaDTO(senha, senhaIgual);
        UsernamePasswordAuthenticationToken dto = new UsernamePasswordAuthenticationToken(1,null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(dto);

        when(tokenService.isValid(any())).thenReturn(dto);
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(getUsuarioEntity()));
        when(passwordEncoder.encode(anyString())).thenReturn("vdhgvc8484dv");

        usuarioService.atualizarSenha(usuarioSenhaDTO , token);

        verify(usuarioRepository, times(1)).save(any());
    }


    @Test(expected = RegraDeNegocioException.class)
    public void deveTertarAtualizarSenhaComErro() throws RegraDeNegocioException {
        String senha = "123456qp";
        String senhaIgual = "123477qp";
        String token = "vksdbvisbvçdmd54v448d4v5e48v4efe5f8fw1";
        UsuarioSenhaDTO usuarioSenhaDTO = new UsuarioSenhaDTO(senha, senhaIgual);
        UsernamePasswordAuthenticationToken dto = new UsernamePasswordAuthenticationToken(1,null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(dto);

        usuarioService.atualizarSenha(usuarioSenhaDTO , token);
    }


    @Test(expected = RegraDeNegocioException.class)
    public void deveTertarAtualizarSenhaComErroDeToken() throws RegraDeNegocioException {
        String senha = "123456qp";
        String senhaIgual = "123477qp";
        String token = "vksdbvisbvçdmd54v448d4v5e48v4efe5f8fw1";
        UsuarioSenhaDTO usuarioSenhaDTO = new UsuarioSenhaDTO(senha, senhaIgual);
        UsernamePasswordAuthenticationToken dto = new UsernamePasswordAuthenticationToken(1,null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(dto);

        doThrow(ExpiredJwtException.class).when(tokenService).isValid(anyString());

        usuarioService.atualizarSenha(usuarioSenhaDTO , token);

        verify(usuarioRepository, times(1)).save(any());
    }


    private static UsuarioEntity getUsuarioEntity() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setNomeCompleto("gustavo lucena silva");
        usuarioEntity.setEmail("gustavo@dbccompany.com.br");
        usuarioEntity.setSenha("uiq@123456");
        usuarioEntity.setIdUsuario(10);

        CargoEntity cargoEntity = new CargoEntity(1, "ROLE_ADMINISTRADOR", new HashSet<>());
        usuarioEntity.getCargos().add(cargoEntity);

        return usuarioEntity;
    }

    private static UsuarioDTO getUsuarioDTO() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setIdUsuario(10);
        usuarioDTO.setNomeCompleto("gustavo lucena silva");
        usuarioDTO.setEmail("gustavo@dbccompany.com.br");

        CargoDTO cargoDTO = new CargoDTO();
        usuarioDTO.setCargo(cargoDTO);

        return usuarioDTO;
    }

    private static UsuarioCreateDTO getUsuarioCreateDTO() {
        UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO();
        usuarioCreateDTO.setNomeCompleto("gustavo lucena silva");
        usuarioCreateDTO.setEmail("gustavo@dbccompany.com.br");
        usuarioCreateDTO.setSenha("uiq@123456");
        usuarioCreateDTO.setSenhaIgual("uiq@123456");

        return usuarioCreateDTO;
    }

    private static CargoEntity getCargoEntity() {
        CargoEntity cargoEntity = new CargoEntity();
        cargoEntity.setIdCargo(1);
        cargoEntity.setNome("ROLE_ADMINISTRADOR");

        return cargoEntity;
    }
}
