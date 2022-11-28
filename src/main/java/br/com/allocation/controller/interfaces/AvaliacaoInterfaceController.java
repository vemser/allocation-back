package br.com.allocation.controller.interfaces;

import br.com.allocation.dto.avaliacaoDTO.AvaliacaoCreateDTO;
import br.com.allocation.dto.avaliacaoDTO.AvaliacaoDTO;
import br.com.allocation.dto.pageDTO.PageDTO;
import br.com.allocation.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public interface AvaliacaoInterfaceController {
    @Operation(summary = "Criar Avalição", description = "Cria uma avaliação e salva no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Avalição Criado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    ResponseEntity<AvaliacaoDTO> salvar(@Valid @RequestBody AvaliacaoCreateDTO avaliacaoCreateDTO) throws RegraDeNegocioException;

    @Operation(summary = "Listar pagina de avalições", description = "Lista uma pagina de avaliações")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Avaliações Listadas com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    ResponseEntity<PageDTO<AvaliacaoDTO>> listar(Integer pagina, Integer tamanho);

    @Operation(summary = "Editar avaliação", description = "Editar uma avaliação e salva no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Avaliação Editada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{id}")
    ResponseEntity<AvaliacaoDTO> editar(@Valid @RequestBody AvaliacaoCreateDTO avaliacaoCreateDTO,
                                        @PathVariable(name = "id") Integer id) throws RegraDeNegocioException;
    @Operation(summary = "Deletar avaliação", description = "Deleta  avaliação do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Avaliação Deletada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idAvaliacao}")
     ResponseEntity<Void> deletar(@PathVariable(name = "idAvaliacao")
                                        Integer id) throws RegraDeNegocioException;
}
