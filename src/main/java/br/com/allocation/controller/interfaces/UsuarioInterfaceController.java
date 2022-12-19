package br.com.allocation.controller.interfaces;

import br.com.allocation.dto.pagedto.PageDTO;
import br.com.allocation.dto.usuariodto.UsuarioCreateDTO;
import br.com.allocation.dto.usuariodto.UsuarioDTO;
import br.com.allocation.dto.usuariodto.UsuarioEditDTO;
import br.com.allocation.enums.Cargos;
import br.com.allocation.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public interface UsuarioInterfaceController {

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
    ResponseEntity<PageDTO<UsuarioDTO>> listar(Integer paginaQueEuQuero, Integer tamanhoDeRegistrosPorPagina);

    @Operation(summary = "Editar o usuário por id", description = "Editar usuário por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Editar o usuário no banco de dados"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idUsuario}")
    ResponseEntity<UsuarioDTO> editar(@RequestParam("cargo") Cargos cargo, @PathVariable("idUsuario") Integer idUsuario, @Valid UsuarioEditDTO usuarioEditDTO) throws RegraDeNegocioException;


    @Operation(summary = "Deleta o usuário por id", description = "Deleta usuário por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Deleta o usuário do banco de dados"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idUsuario}")
    ResponseEntity<Void> deletar(@PathVariable("idUsuario") Integer idUsuario) throws RegraDeNegocioException;

    @Operation(summary = "Listar por usuario por email", description = "Listar por usuario por email")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Listar por usuario por email"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listarPorEmail")
    ResponseEntity<PageDTO<UsuarioDTO>> listarPorEmail(Integer pagina, Integer tamanho, @RequestParam("email") String email) throws RegraDeNegocioException;

    @Operation(summary = "Listar usuario por nome paginado", description = "Listar por usuario por nome paginado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Listar por usuario por nome paginado do banco"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listarPorNome")
    ResponseEntity<PageDTO<UsuarioDTO>> listarPorNome(Integer pagina, Integer tamanho, String nome);

    @Operation(summary = "Listar usuario por cargo paginado", description = "Listar por usuario por come paginado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Listar por usuario por cargo paginado do banco"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listarPorCargo")
    ResponseEntity<PageDTO<UsuarioDTO>> listarPorCargo(Integer pagina, Integer tamanho, Cargos cargos) throws RegraDeNegocioException;
}
