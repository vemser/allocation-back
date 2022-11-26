package br.com.allocation.controller;

import br.com.allocation.dto.ProgramaDTO.ProgramaCreateDTO;
import br.com.allocation.dto.ProgramaDTO.ProgramaDTO;
import br.com.allocation.dto.pageDTO.PageDTO;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.service.ProgramaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/programa")
@Validated
@RequiredArgsConstructor
@Slf4j
public class ProgramaController {

    private final ProgramaService programaService;

    @Operation(summary = "Criar programa", description = "Cria um programa no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Programa Criado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<ProgramaDTO> salvar(@Valid @RequestBody ProgramaCreateDTO programaCreate) {
        log.info("Adicionando o Usuário...");
        ProgramaDTO programa = programaService.salvar(programaCreate);
        log.info("Usuário adicionado com sucesso!");
        return new ResponseEntity<>(programa, HttpStatus.CREATED);
    }

    @Operation(summary = "Listar pagina de programas", description = "Lista uma pagina de programas")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Programas Listados com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<PageDTO<ProgramaDTO>> listar(Integer pagina, Integer tamanho){
        return ResponseEntity.ok(programaService.listar(pagina, tamanho));
    }

    @Operation(summary = "Editar programa", description = "Editar um programa no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Programa Editado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/{id}")
    public ResponseEntity<ProgramaDTO> editar(@Valid @RequestBody ProgramaCreateDTO programaCreate, @PathVariable(name = "id") Integer id) throws RegraDeNegocioException {
        log.info("Editando o Programa...");
        ProgramaDTO programa = programaService.editar(id, programaCreate);
        log.info("Programa editado com sucesso!");
        return new ResponseEntity<>(programa, HttpStatus.CREATED);
    }

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
    public ResponseEntity<Void> deletar(@PathVariable(name = "idPrograma") Integer idUsuario) throws RegraDeNegocioException {
        programaService.deletar(idUsuario);
        log.info("Programa deletado com sucesso");
        return ResponseEntity.noContent().build();
    }
}
