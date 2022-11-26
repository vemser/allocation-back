package br.com.allocation.controller;

import br.com.allocation.dto.ClienteDTO.ClienteCreateDTO;
import br.com.allocation.dto.ClienteDTO.ClienteDTO;
import br.com.allocation.dto.pageDTO.PageDTO;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.service.ClienteService;
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
@RequestMapping("/cliente")
@Validated
@RequiredArgsConstructor
@Slf4j
public class ClienteController {

    private final ClienteService clienteService;

    @Operation(summary = "Criar cliente", description = "Cria um cliente no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Cliente Criado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<ClienteDTO> salvar(@Valid @RequestBody ClienteCreateDTO clienteCreate) {
        log.info("Adicionando o Usuário...");
        ClienteDTO cliente = clienteService.salvar(clienteCreate);
        log.info("Usuário adicionado com sucesso!");
        return new ResponseEntity<>(cliente, HttpStatus.CREATED);
    }

    @Operation(summary = "Listar pagina de clientes", description = "Lista uma pagina de clientes")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Clientes Listados com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public ResponseEntity<PageDTO<ClienteDTO>> listar(Integer pagina, Integer tamanho) {
        return ResponseEntity.ok(clienteService.listar(pagina, tamanho));
    }

    @Operation(summary = "Editar cliente", description = "Editar um cliente no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Cliente Editado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/{id}")
    public ResponseEntity<ClienteDTO> editar(@Valid @RequestBody ClienteCreateDTO clienteCreate, @PathVariable(name = "id") Integer id) throws RegraDeNegocioException {
        log.info("Editando o Cliente...");
        ClienteDTO cliente = clienteService.editar(id, clienteCreate);
        log.info("Cliente editado com sucesso!");
        return new ResponseEntity<>(cliente, HttpStatus.CREATED);
    }

    @Operation(summary = "Deletar cliente", description = "Deleta o cliente no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Deletado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void> deletar(@PathVariable(name = "idUsuario") Integer idUsuario) throws RegraDeNegocioException {
        clienteService.deletar(idUsuario);
        log.info("Usuário deletado com sucesso");
        return ResponseEntity.noContent().build();
    }
}
