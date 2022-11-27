package br.com.allocation.controller.interfaces;

import br.com.allocation.dto.alunoDTO.AlunoCreateDTO;
import br.com.allocation.dto.alunoDTO.AlunoDTO;
import br.com.allocation.dto.pageDTO.PageDTO;
import br.com.allocation.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public interface AlunoInterfaceController {
    @Operation(summary = "Criar Aluno", description = "Cria um aluno no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Aluno Criado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    ResponseEntity<AlunoDTO> salvar(@Valid @RequestBody AlunoCreateDTO alunoCreate);

    @Operation(summary = "Listar pagina de alunos", description = "Lista uma pagina de alunos")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Alunos Listados com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    ResponseEntity<PageDTO<AlunoDTO>> listar(Integer pagina, Integer tamanho);

    @Operation(summary = "Editar aluno", description = "Editar um aluno no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Aluno Editado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping
    ResponseEntity<AlunoDTO> editar(@Valid @RequestBody AlunoCreateDTO alunoCreate,
                                    @PathVariable(name = "id") Integer id) throws RegraDeNegocioException;

    @Operation(summary = "Deletar aluno", description = "Deleta o aluno no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Aluno Deletado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping
    ResponseEntity<Void> deletar(@PathVariable(name = "idAluno")
                                        Integer id) throws RegraDeNegocioException;
}
