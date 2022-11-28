package br.com.allocation.controller.interfaces;

import br.com.allocation.dto.pageDTO.PageDTO;
import br.com.allocation.dto.usuarioDTO.MensagemDTO;
import br.com.allocation.dto.usuarioDTO.UsuarioCreateDTO;
import br.com.allocation.dto.usuarioDTO.UsuarioDTO;
import br.com.allocation.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

public interface UsuarioInterfaceController {

    @Operation(summary = "Criar um registro de usuario.", description = "Cria um cadastro de usuario no banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cria usuario."),
                    @ApiResponse(responseCode = "200", description = "recupera dados do usuario logado no banco de dados."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    ResponseEntity<UsuarioDTO> create(
            @RequestBody @Valid UsuarioCreateDTO usuarioCreateDTO)
            throws RegraDeNegocioException, IOException;

    @Operation(summary = "Upload na imagem", description = "upload na foto de perfil do usuario")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "upload da imagem do usuario no banco de dados."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    ResponseEntity<MensagemDTO> uploadFile(@RequestParam("file") MultipartFile file,
                                           @RequestParam("email") String email);

    @Operation(summary = "recupera  imagem do usuario", description = "recupera foto de perfil do usuario")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "recupera imagem do usuario no banco de dados."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    ResponseEntity<String> recuperarImagem(@RequestParam("email") String email) throws RegraDeNegocioException;

    @Operation(summary = "Listar todos os usuários", description = "Listar todos os usuários")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Listar todos os usuários do banco"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listAllUsers")
    ResponseEntity<PageDTO<UsuarioDTO>> listarUsuarioPaginado(Integer paginaQueEuQuero, Integer tamanhoDeRegistrosPorPagina);

    @Operation(summary = "Editar o usuário por id", description = "Editar usuário por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Editar o usuário no banco de dados"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/editar")
    ResponseEntity<UsuarioDTO> editar(Integer id, UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException;


    @Operation(summary = "Deleta o usuário por id", description = "Deleta usuário por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Deleta o usuário do banco de dados"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/deletar/{id}")
    ResponseEntity<Void> deletar(Integer id) throws RegraDeNegocioException;

}
