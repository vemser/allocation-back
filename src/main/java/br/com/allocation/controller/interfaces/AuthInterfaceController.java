package br.com.allocation.controller.interfaces;

import br.com.allocation.dto.loginDTO.LoginDTO;
import br.com.allocation.dto.loginDTO.LoginWithIdDTO;
import br.com.allocation.dto.usuarioDTO.MensagemDTO;
import br.com.allocation.dto.usuarioDTO.UsuarioCreateDTO;
import br.com.allocation.dto.usuarioDTO.UsuarioDTO;
import br.com.allocation.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

public interface AuthInterfaceController {
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
