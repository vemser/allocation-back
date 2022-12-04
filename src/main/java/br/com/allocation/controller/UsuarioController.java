package br.com.allocation.controller;

import br.com.allocation.controller.interfaces.UsuarioInterfaceController;
import br.com.allocation.dto.pageDTO.PageDTO;
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

import javax.validation.Valid;


@RestController
@RequestMapping("/usuario")
@Validated
@RequiredArgsConstructor
@Slf4j
public class UsuarioController implements UsuarioInterfaceController {
    private final UsuarioService usuarioService;
    private final FileService fileService;


    @Override
    public ResponseEntity<String> recuperarImagem(@RequestParam("email") String email) throws RegraDeNegocioException {
        return new ResponseEntity<>(fileService.getImage(email), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PageDTO<UsuarioDTO>> listar(Integer pagina, Integer tamanho) {
        return new ResponseEntity<>(usuarioService.listar(pagina, tamanho), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PageDTO<UsuarioDTO>> listarPorEmail(Integer pagina, Integer tamanho, String email) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.listarPorEmailPag(pagina, tamanho, email), HttpStatus.OK);
    }

    @GetMapping("/listarPorNome")
    public ResponseEntity<PageDTO<UsuarioDTO>> listarPorNome(Integer pagina, Integer tamanho, String nome) {
        return new ResponseEntity<>(usuarioService.listarPorNome(pagina, tamanho, nome), HttpStatus.OK);
    }

    @GetMapping("/listarPorCargo")
    public ResponseEntity<PageDTO<UsuarioDTO>> listarPorCargo(Integer pagina, Integer tamanho, Cargos cargos) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.listarPorCargo(pagina, tamanho, cargos), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UsuarioDTO> editar(@RequestParam("cargo") Cargos cargo, Integer idUsuario, @Valid @RequestBody UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.editar(idUsuario, usuarioCreateDTO, cargo), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deletar(Integer idUsuario) throws RegraDeNegocioException {
        usuarioService.deletar(idUsuario);
        log.info("Usuário deletado com sucesso");
        return ResponseEntity.noContent().build();
    }

}
