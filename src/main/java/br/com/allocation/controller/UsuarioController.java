package br.com.allocation.controller;

import br.com.allocation.controller.interfaces.UsuarioInterfaceController;
import br.com.allocation.dto.pageDTO.PageDTO;
import br.com.allocation.dto.usuarioDTO.MensagemDTO;
import br.com.allocation.dto.usuarioDTO.UsuarioCreateDTO;
import br.com.allocation.dto.usuarioDTO.UsuarioDTO;
import br.com.allocation.enums.Cargos;
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
public class UsuarioController implements UsuarioInterfaceController {
    private final UsuarioService usuarioService;
    private final FileService fileService;


    @GetMapping("/recuperarImagem")
    public ResponseEntity<String> recuperarImagem(@RequestParam("email") String email) throws RegraDeNegocioException {
        return new ResponseEntity<>(fileService.getImage(email), HttpStatus.OK);
    }

    @GetMapping("/listAllUsers")
    public ResponseEntity<PageDTO<UsuarioDTO>> listarUsuarioPaginado(Integer paginaQueEuQuero, Integer tamanhoDeRegistrosPorPagina){
        return new ResponseEntity<>(usuarioService.listar(paginaQueEuQuero, tamanhoDeRegistrosPorPagina), HttpStatus.OK);
    }

    @PutMapping("/editar")
    public ResponseEntity<UsuarioDTO> editar(
            @RequestParam("cargo") Cargos cargo,
            Integer id, UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.editar(id, usuarioCreateDTO, cargo), HttpStatus.OK);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(Integer id) throws RegraDeNegocioException {
        usuarioService.deletar(id);
        log.info("Usuário deletado com sucesso");
        return ResponseEntity.noContent().build();
    }

}
