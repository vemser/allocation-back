package br.com.allocation.controller.interfaces;

import br.com.allocation.dto.usuarioDTO.UsuarioCargosDTO;
import br.com.allocation.dto.usuarioDTO.UsuarioDTO;
import br.com.allocation.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface CargoInterfaceController {


    @Operation(summary = "Editar Cargo do usuario", description = "Editar um cargo e salva no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Cargo Editada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/atualizar")
    public ResponseEntity<UsuarioDTO> atualizarCargo(@RequestBody @Valid UsuarioCargosDTO usuarioCargosDTO) throws RegraDeNegocioException;
}
