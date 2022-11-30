package br.com.allocation.controller.interfaces;

import br.com.allocation.dto.programaDTO.ProgramaCreateDTO;
import br.com.allocation.dto.programaDTO.ProgramaDTO;
import br.com.allocation.dto.pageDTO.PageDTO;
import br.com.allocation.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public interface ProgramaInterfaceController {
    @Operation(summary = "Criar programa", description = "Cria um programa no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Programa Criado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    ResponseEntity<ProgramaDTO> salvar(@Valid @RequestBody ProgramaCreateDTO programaCreate);

    @Operation(summary = "Listar pagina de programas", description = "Lista uma pagina de programas")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Programas Listados com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    ResponseEntity<PageDTO<ProgramaDTO>> listar(Integer pagina, Integer tamanho);

    @Operation(summary = "Editar programa", description = "Editar um programa no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Programa Editado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idPrograma}")
    ResponseEntity<ProgramaDTO> editar(@Valid @RequestBody ProgramaCreateDTO programaCreate,
                                              @PathVariable(name = "idPrograma") Integer idPrograma) throws RegraDeNegocioException;
    @Operation(summary = "Deletar programa", description = "Deleta o programa no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Deletado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idPrograma}")
    public ResponseEntity<Void> deletar(@PathVariable(name = "idPrograma") Integer idPrograma) throws RegraDeNegocioException;
}
