package br.com.allocation.service;


import br.com.allocation.dto.usuarioDTO.UsuarioCreateDTO;
import br.com.allocation.dto.usuarioDTO.UsuarioDTO;
import br.com.allocation.entity.FileEntity;
import br.com.allocation.entity.UsuarioEntity;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;

    public UsuarioDTO create(UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException, IOException {
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



    public UsuarioDTO findById(Integer id) throws RegraDeNegocioException{
        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Usuario não encontrado!"));
        return objectMapper.convertValue(usuario, UsuarioDTO.class);
    }
    public Optional<UsuarioEntity> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public UsuarioEntity findUsuarioByEmail(String email) throws RegraDeNegocioException {
        UsuarioEntity usuario = usuarioRepository.findUsuarioEntityByEmail(email);
        if(usuario == null) {
            throw new RegraDeNegocioException("Usuario não encontrado!");
        }
        else{
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
