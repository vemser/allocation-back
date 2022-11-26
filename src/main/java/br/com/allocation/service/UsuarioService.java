package br.com.allocation.service;


import br.com.allocation.dto.UsuarioCreateDTO;
import br.com.allocation.dto.UsuarioDTO;
import br.com.allocation.entity.UsuarioEntity;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.allocation.controller.entity.UsuarioEntity;
import br.com.allocation.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;

    public UsuarioDTO create(UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {
        //verificar se usuario existe
        if (findByEmail(usuarioCreateDTO.getEmail()).isPresent()) {
            throw new RegraDeNegocioException("Já existe uma conta com esse email");
        }
        //verificar senha fraca,senha media e forte
        if (usuarioCreateDTO.getSenha().length() > 6) {
        }
        UsuarioEntity usuarioEntity = converterEntity(usuarioCreateDTO);
        String encode = passwordEncoder.encode(usuarioEntity.getSenha());
        usuarioEntity.setSenha(encode);
        UsuarioDTO usuarioDTO = converterEmDTO(usuarioRepository.save(usuarioEntity));
        return usuarioDTO;
    }

    public Optional<UsuarioEntity> findByEmail(String email) {
        Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findByEmail(email);
        return usuarioEntity;
    }

    private UsuarioEntity converterEntity(UsuarioCreateDTO usuarioCreateDTO) {
        return objectMapper.convertValue(usuarioCreateDTO, UsuarioEntity.class);
    }

    public UsuarioDTO converterEmDTO(UsuarioEntity usuarioEntity) {
        UsuarioDTO usuarioDTO = new UsuarioDTO(usuarioEntity.getNomeCompleto(),
                usuarioEntity.getEmail(), usuarioEntity.getFoto());
        return usuarioDTO;


    private UsuarioRepository usuarioRepository;

    public Optional<UsuarioEntity> findByEmail(String email){
        return usuarioRepository.findByEmail(email);
    }

}
