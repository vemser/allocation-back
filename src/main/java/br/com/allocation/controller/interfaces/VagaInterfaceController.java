package br.com.allocation.controller.interfaces;

import br.com.allocation.dto.pageDTO.PageDTO;
import br.com.allocation.dto.vagaDTO.VagaCreateDTO;
import br.com.allocation.dto.vagaDTO.VagaDTO;
import br.com.allocation.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public interface VagaInterfaceController {

    @Operation(summary = "Criar um registro de vaga.", description = "Cria um cadastro de vaga no banco de dados.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cria vaga."),
                    @ApiResponse(responseCode = "200", description = "recupera dados do vaga logado no banco de dados."),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    ResponseEntity<VagaDTO> salvar(@RequestBody @Valid VagaCreateDTO vagaCreateDTO) throws RegraDeNegocioException;

    @Operation(summary = "Listar todos as vagas", description = "Listar todos as vagas")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Listar todos as vagas do banco"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    ResponseEntity<PageDTO<VagaDTO>> listar(Integer pagina, Integer tamanho);

    @Operation(summary = "Editar a vaga por id", description = "Editar vaga por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Editar a vaga no banco de dados"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/editar")
    ResponseEntity<VagaDTO> editar(Integer id, VagaCreateDTO vagaCreateDTO) throws RegraDeNegocioException;


    @Operation(summary = "Deleta a vaga por id", description = "Deleta vaga por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Deleta a vaga do banco de dados"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/deletar/{id}")
    ResponseEntity<Void> deletar(Integer id) throws RegraDeNegocioException;
}
