package br.com.allocation.service;

import br.com.allocation.dto.usuarioDTO.FileDTO;
import br.com.allocation.entity.FileEntity;
import br.com.allocation.entity.UsuarioEntity;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.repository.FileRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final UsuarioService usuarioService;

    private final ObjectMapper objectMapper;

    public FileDTO store(MultipartFile file, String email) throws IOException, RegraDeNegocioException {
        try {
            UsuarioEntity usuario = usuarioService.findUsuarioEntityByEmail(email);
            FileEntity fileEntityExiste = fileRepository.findFileEntitiesByUsuario(usuario);
            FileEntity fileDB = new FileEntity();
            if(fileEntityExiste != null){
                fileDB = fileEntityExiste;
            }
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            fileDB.setName(fileName);
            fileDB.setType(file.getContentType());
            fileDB.setData(file.getBytes());

            fileDB.setUsuario(usuario);
            FileEntity fileEntity = fileRepository.save(fileDB);
            FileDTO fileDTO = objectMapper.convertValue(fileEntity, FileDTO.class);
            return fileDTO;
            }
        catch (Exception e){
            throw new RegraDeNegocioException("Ocorreu um erro ao enviar a imagem!");
        }
    }

    public String getImage(String email) throws RegraDeNegocioException {
        UsuarioEntity usuario = usuarioService.findUsuarioEntityByEmail(email);
        FileEntity fileEntity = fileRepository.findByUsuario(usuario).orElseThrow(() -> new RegraDeNegocioException("Imagem não encontrada ou não existe."));
        return Base64Utils.encodeToString(fileEntity.getData());

    }

}
