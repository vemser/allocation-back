package br.com.allocation.controller;

import br.com.allocation.dto.pageDTO.PageDTO;
import br.com.allocation.dto.usuarioDTO.MensagemDTO;
import br.com.allocation.dto.usuarioDTO.UsuarioCreateDTO;
import br.com.allocation.dto.usuarioDTO.UsuarioDTO;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.service.FileService;
import br.com.allocation.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;


@RestController
@RequestMapping("/usuario")
@Validated
@RequiredArgsConstructor
@Slf4j
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final FileService fileService;

    @PostMapping("/register")
    public ResponseEntity<UsuarioDTO> create(
            @RequestBody @Valid UsuarioCreateDTO usuarioCreateDTO)
            throws RegraDeNegocioException, IOException {

        return ResponseEntity.ok(usuarioService.create(usuarioCreateDTO));
    }
    @PostMapping("/upload/")
    public ResponseEntity<MensagemDTO> uploadFile(@RequestParam("file") MultipartFile file,
                                                  @RequestParam("email") String email) {
        String message = "";
        try {
            fileService.store(file, email);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new MensagemDTO(message));
        } catch (Exception e) {
            e.printStackTrace();
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MensagemDTO(message));
        }
    }

    @GetMapping("/recuperarImagem")
    public ResponseEntity<String> recuperarImagem(@RequestParam("email") String email) throws RegraDeNegocioException {
        return new ResponseEntity<>(fileService.getImage(email), HttpStatus.OK);
    }

    @GetMapping("/listAllUsers")
    public ResponseEntity<PageDTO<UsuarioDTO>> listarUsuarioPaginado(Integer paginaQueEuQuero, Integer tamanhoDeRegistrosPorPagina){
        return new ResponseEntity<>(usuarioService.listar(paginaQueEuQuero, tamanhoDeRegistrosPorPagina), HttpStatus.OK);
    }

    @PutMapping("/editar")
    public ResponseEntity<UsuarioDTO> editar(Integer id, UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.editar(id, usuarioCreateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(Integer id) throws RegraDeNegocioException {
        usuarioService.deletar(id);
        log.info("Usuário deletado com sucesso");
        return ResponseEntity.noContent().build();
    }

}
