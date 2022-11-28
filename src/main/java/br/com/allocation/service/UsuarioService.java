package br.com.allocation.service;


import br.com.allocation.dto.loginDTO.LoginWithIdDTO;
import br.com.allocation.dto.pageDTO.PageDTO;
import br.com.allocation.dto.usuarioDTO.UsuarioCreateDTO;
import br.com.allocation.dto.usuarioDTO.UsuarioDTO;
import br.com.allocation.entity.CargoEntity;
import br.com.allocation.entity.UsuarioEntity;
import br.com.allocation.enums.Cargos;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    private final CargoService cargoService;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;

    public UsuarioDTO create(UsuarioCreateDTO usuarioCreateDTO, Cargos cargos) throws RegraDeNegocioException {
        verificarEmail(findByEmail(usuarioCreateDTO.getEmail()).isPresent());
        validarSenha(usuarioCreateDTO);
        confirmarSenha(usuarioCreateDTO);

        CargoEntity cargo = cargoService.findByNome(String.valueOf(cargos.getDescricao()));

        UsuarioEntity usuarioEntity = converterEntity(usuarioCreateDTO);
        usuarioEntity.getCargos().add(cargo);

        String encode = passwordEncoder.encode(usuarioEntity.getSenha());
        usuarioEntity.setSenha(encode);
        return converterEmDTO(usuarioRepository.save(usuarioEntity));
    }

    public UsuarioDTO editar(Integer id, UsuarioCreateDTO usuarioCreateDTO, Cargos cargos) throws RegraDeNegocioException {
        UsuarioEntity usuario = findById(id);

        usuario.setEmail(usuarioCreateDTO.getEmail());
        usuario.setSenha(passwordEncoder.encode(usuarioCreateDTO.getSenha()));
        usuario.setNomeCompleto(usuarioCreateDTO.getNomeCompleto());

        CargoEntity cargo = cargoService.findByNome(cargos.getDescricao());
        usuario.getCargos().clear();
        usuario.getCargos().add(cargo);

        usuarioRepository.save(usuario);
        return converterEmDTO(usuario);
    }

    public List<UsuarioDTO> findAllUsers(){
        return usuarioRepository.findAll()
                .stream()
                .map(this::converterEmDTO)
                .toList();
    }

    public PageDTO<UsuarioDTO> listar(Integer pagina, Integer tamanho) {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<UsuarioEntity> paginaDoRepositorio = usuarioRepository.findAll(pageRequest);
        List<UsuarioDTO> usuarios = paginaDoRepositorio.getContent().stream()
                .map(usuario -> objectMapper.convertValue(usuario, UsuarioDTO.class))
                .toList();
        return new PageDTO<>(paginaDoRepositorio.getTotalElements(),
                paginaDoRepositorio.getTotalPages(),
                pagina,
                tamanho,
                usuarios
        );
    }

    public void deletar(Integer id) throws RegraDeNegocioException {
        this.findById(id);
        usuarioRepository.deleteById(id);
    }

    private static void confirmarSenha(UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {
        if (!usuarioCreateDTO.getSenha().equals(usuarioCreateDTO.getSenhaIgual())){
            throw new RegraDeNegocioException("Senha diferente!");
        }
    }

    private void verificarEmail(boolean usuarioCreateDTO) throws RegraDeNegocioException {
        if (usuarioCreateDTO) {
            throw new RegraDeNegocioException("Já existe uma conta com esse email!");
        }
    }

    private static void validarSenha(UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {
        int countLetras = 0;
        int countNumeros = 0;
        if (usuarioCreateDTO.getSenha().length() < 6) {
            throw new RegraDeNegocioException("senha fraca");
        }
        for (int i = 0; i < usuarioCreateDTO.getSenha().length(); i++) {
            char caracter = usuarioCreateDTO.getSenha().charAt(i);
            if (Character.isAlphabetic(caracter)) {
                countLetras++;
            } else if (Character.isDigit(caracter)) {
                countNumeros++;
            }
        }
        if (countLetras < 2 || countNumeros < 2)
            throw new RegraDeNegocioException("Senha fraca");
    }

    public UsuarioEntity findById(Integer id) throws RegraDeNegocioException {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Usuario não encontrado!"));
    }

    public Integer getIdLoggedUser() {
        return Integer.parseInt((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    public LoginWithIdDTO getLoggedUser() throws RegraDeNegocioException {
        UsuarioEntity userLogged = findById(getIdLoggedUser());
        return objectMapper.convertValue(userLogged, LoginWithIdDTO.class);
    }

    public Optional<UsuarioEntity> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public UsuarioEntity findUsuarioByEmail(String email) throws RegraDeNegocioException {
        UsuarioEntity usuario = usuarioRepository.findUsuarioEntityByEmail(email);
        if (usuario == null) {
            throw new RegraDeNegocioException("Usuario não encontrado!");
        } else {
            return usuario;
        }
    }

    private UsuarioEntity converterEntity(UsuarioCreateDTO usuarioCreateDTO) {
        return objectMapper.convertValue(usuarioCreateDTO, UsuarioEntity.class);
    }

    public UsuarioDTO converterEmDTO(UsuarioEntity usuarioEntity) {
        return objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);
    }


}
