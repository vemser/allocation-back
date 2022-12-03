package br.com.allocation.controller.interfaces;

import br.com.allocation.dto.loginDTO.LoginDTO;
import br.com.allocation.dto.loginDTO.LoginWithIdDTO;
import br.com.allocation.dto.usuarioDTO.*;
import br.com.allocation.entity.FileEntity;
import br.com.allocation.enums.Cargos;
import br.com.allocation.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

public interface AuthInterfaceController {

    @Operation(summary = "Criar um registro de usuario.", description = "Cria um cadastro de usuario no banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Cria usuario."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/register")
    ResponseEntity<UsuarioDTO> create(
            @RequestParam("cargo") Cargos cargo,
            @RequestBody @Valid UsuarioCreateDTO usuarioCreateDTO)
            throws RegraDeNegocioException, IOException;

    @Operation(summary = "Recuperar senha.", description = "Envia um email para o usuario trocar a senha.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Envia um email para trocar senha do usuario."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/recuperar-senha")
    public ResponseEntity<String> recuperarSenha(@RequestParam("email") String email) throws RegraDeNegocioException;

    @Operation(summary = "Atualizar senha.", description = "Atualiza a senha do usuario.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Atualiza senha do usuario."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/atualizar-senha/{token}")
    public ResponseEntity<String> atualizarSenha(@RequestBody UsuarioSenhaDTO usuarioSenhaDTO, @PathVariable("token") String token) throws RegraDeNegocioException;

    @Operation(summary = "Upload na imagem", description = "upload na foto de perfil do usuario")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "upload da imagem do usuario no banco de dados."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/upload/")
    ResponseEntity<FileDTO> uploadFile(@RequestParam("file") MultipartFile file,
                                       @RequestParam("email") String email) throws RegraDeNegocioException, IOException;

    @Operation(summary = "Logar com email do usuario.", description = "Loga no sistema com login email.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Loga no sistema com um login."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    ResponseEntity<String> auth(@RequestBody @Valid LoginDTO loginDTO);

    @Operation(summary = "recupera dados do usuario logado",
            description = "recupera dados do usuario logado no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "recupera dados do usuario logado no banco de dados."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/logged")
    ResponseEntity<LoginWithIdDTO> loggedVerify() throws RegraDeNegocioException;
}
