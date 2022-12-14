package br.com.allocation.controller.interfaces;

import br.com.allocation.dto.pagedto.PageDTO;
import br.com.allocation.dto.reservaAlocacaodto.ReservaAlocacaoCreateDTO;
import br.com.allocation.dto.reservaAlocacaodto.ReservaAlocacaoDTO;
import br.com.allocation.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public interface ReservaAlocacaoInterface {
    @Operation(summary = "Criar Reserva ou Alocação", description = "Cria uma Reserva e Alocação e salva no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Reserva e Alocação  Criado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    ResponseEntity<ReservaAlocacaoDTO> salvar(@Valid @RequestBody ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO) throws RegraDeNegocioException;

    @Operation(summary = "Listar pagina de Reserva alocação", description = "Lista pagina Reserva alocação")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "20", description = "Reserva alocação Listadas com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    ResponseEntity<PageDTO<ReservaAlocacaoDTO>> listar(Integer pagina, Integer tamanho);

    @Operation(summary = "Filtra pagina de Reserva alocação", description = "Filtra pagina Reserva alocação")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Reserva alocação Listadas com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/filtro")
    public ResponseEntity<PageDTO<ReservaAlocacaoDTO>> filtrar(Integer pagina, Integer tamanho, @RequestParam(name = "nomeAluno", required = false) String nomeAluno, @RequestParam(name = "nomeVaga", required = false) String nomeVaga);


    @Operation(summary = "Editar Reserva alocação", description = "Editar uma Reserva alocação e salva no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Reserva alocação Editada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idReservaAlocacao}")
    ResponseEntity<ReservaAlocacaoDTO> editar(@Valid @RequestBody ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO,
                                              @PathVariable(name = "idReservaAlocacao") Integer idReservaAlocacao) throws RegraDeNegocioException;

    @Operation(summary = "Deletar Reserva alocação", description = "Deleta  Reserva alocação do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Reserva alocação Deletada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idReservaAlocacao}")
    ResponseEntity<Void> deletar(@PathVariable(name = "idReservaAlocacao") Integer idReservaAlocacao) throws RegraDeNegocioException;
}
