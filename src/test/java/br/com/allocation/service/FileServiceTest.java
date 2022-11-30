package br.com.allocation.service;

import br.com.allocation.entity.FileEntity;
import br.com.allocation.entity.UsuarioEntity;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.repository.FileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FileServiceTest {

    @InjectMocks
    private FileService fileService;

    @Mock
    private FileRepository fileRepository;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private StringUtils stringUtils;

    @Test
    public void deveTestarStoreComSucesso() throws RegraDeNegocioException, IOException {
        //Setup
        FileEntity fileEntity = getFileEntity();
        UsuarioEntity usuario = getUsuarioEntity();
        String filename = "fotodeperfil";
        byte[] arquivo = "aasda".getBytes();
        MultipartFile file = new MultipartFile() {
            @Override
            public String getName() {
                return filename;
            }

            @Override
            public String getOriginalFilename() {
                return filename;
            }

            @Override
            public String getContentType() {
                return "png";
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return 0;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return arquivo;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {

            }
        };

        when(usuarioService.findUsuarioEntityByEmail(anyString())).thenReturn(usuario);
        when(fileRepository.save(any())).thenReturn(fileEntity);
        //ACT
        FileEntity fileEntity1 = fileService.store(file, usuario.getEmail());

        //ASSERT
        assertNotNull(fileEntity1);

    }

    @Test
    public void deveTestarGetImageComSucesso() throws RegraDeNegocioException {
        //SETUP
        UsuarioEntity usuario = getUsuarioEntity();
        FileEntity fileEntity = getFileEntity();
        String email = "kaio@gmail.com";
        when(usuarioService.findUsuarioEntityByEmail(anyString())).thenReturn(usuario);
        when(fileRepository.findByUsuario(any())).thenReturn(Optional.of(fileEntity));
        //ACT
        String imagem = fileService.getImage(email);

        //ASSERT
        assertNotNull(imagem);
    }

    private static FileEntity getFileEntity(){
        FileEntity fileEntity = new FileEntity();
        byte[] arquivo = "aasda".getBytes();
        fileEntity.setId(1);
        fileEntity.setName("fotodeperfil");
        fileEntity.setType("png");
        fileEntity.setData(arquivo);
        return fileEntity;
    }
    private static UsuarioEntity getUsuarioEntity(){
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setIdUsuario(1);
        usuario.setNomeCompleto("Kaio Antonio");
        usuario.setSenha("1234");
        usuario.setEmail("kaio@gmail.com");
        return usuario;
    }

}
