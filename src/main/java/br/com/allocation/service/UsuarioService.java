package br.com.allocation.service;


import br.com.allocation.dto.pageDTO.PageDTO;
import br.com.allocation.dto.usuarioDTO.UsuarioCreateDTO;
import br.com.allocation.dto.usuarioDTO.UsuarioDTO;
import br.com.allocation.entity.UsuarioEntity;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;

    public UsuarioDTO create(UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {
        verificarEmail(findByEmail(usuarioCreateDTO.getEmail()).isPresent());
        validarSenha(usuarioCreateDTO);
        confirmarSenha(usuarioCreateDTO);
        UsuarioEntity usuarioEntity = converterEntity(usuarioCreateDTO);
        String encode = passwordEncoder.encode(usuarioEntity.getSenha());
        usuarioEntity.setSenha(encode);
        UsuarioDTO usuarioDTO = converterEmDTO(usuarioRepository.save(usuarioEntity));
        return usuarioDTO;
    }
    public UsuarioDTO editar(Integer id, UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {
        this.findById(id);
        UsuarioEntity usuarioEntity = converterEntity(usuarioCreateDTO);
        UsuarioDTO usuarioDTO = converterEmDTO(usuarioRepository.save(usuarioEntity));
        return usuarioDTO;
    }
    public PageDTO<UsuarioDTO> listar(Integer pagina, Integer tamanho) {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<UsuarioEntity> paginaRepository = usuarioRepository.findAll(pageRequest);

        List<UsuarioDTO> usuarioDTOS = usuarioRepository.findAll().stream()
                .map(this::converterEmDTO)
                .collect(Collectors.toList());
        return new PageDTO<>(paginaRepository.getTotalElements(),
                paginaRepository.getTotalPages(),
                pagina,
                tamanho,
                usuarioDTOS);
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
        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Usuario não encontrado!"));
        return usuario;
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
