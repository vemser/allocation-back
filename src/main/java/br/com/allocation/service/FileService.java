package br.com.allocation.service;

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

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final UsuarioService usuarioService;
    private final ObjectMapper objectMapper;

    public FileEntity store(MultipartFile file, String email) throws IOException, RegraDeNegocioException {
        UsuarioEntity usuario= usuarioService.findUsuarioByEmail(email);
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileEntity fileDB = new FileEntity();
        fileDB.setName(fileName);
        fileDB.setType(file.getContentType());
        fileDB.setData(file.getBytes());
        fileDB.setUsuario(usuario);
        return fileRepository.save(fileDB);
    }

    public String getImage(String email) throws RegraDeNegocioException {
        UsuarioEntity usuario = usuarioService.findUsuarioByEmail(email);
        FileEntity fileEntity = fileRepository.findByUsuario(usuario);
        return Base64Utils.encodeToString(fileEntity.getData());

    }

}
