package br.com.allocation.controller;

import br.com.allocation.controller.interfaces.ClienteInterfaceController;
import br.com.allocation.dto.clienteDTO.ClienteCreateDTO;
import br.com.allocation.dto.clienteDTO.ClienteDTO;
import br.com.allocation.dto.pageDTO.PageDTO;
import br.com.allocation.enums.Situacao;
import br.com.allocation.enums.SituacaoCliente;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.service.ClienteService;
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
public class ClienteController implements ClienteInterfaceController {

    private final ClienteService clienteService;

    @Override
    @PostMapping
    public ResponseEntity<ClienteDTO> salvar(@Valid @RequestBody ClienteCreateDTO clienteCreate, SituacaoCliente situacao) {
        log.info("Adicionando o Usuário...");
        ClienteDTO cliente = clienteService.salvar(clienteCreate, situacao);
        log.info("Usuário adicionado com sucesso!");
        return new ResponseEntity<>(cliente, HttpStatus.CREATED);
    }

    @Override
    @GetMapping
    public ResponseEntity<PageDTO<ClienteDTO>> listar(Integer pagina, Integer tamanho) {
        return ResponseEntity.ok(clienteService.listar(pagina, tamanho));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> editar(@RequestParam("situacao") SituacaoCliente situacaoCliente,
            @Valid @RequestBody ClienteCreateDTO clienteCreate,
                                             @PathVariable(name = "id") Integer id) throws RegraDeNegocioException {
        log.info("Editando o Cliente...");
        ClienteDTO cliente = clienteService.editar(id, clienteCreate, situacaoCliente);
        log.info("Cliente editado com sucesso!");
        return new ResponseEntity<>(cliente, HttpStatus.CREATED);
    }

    @Override
    @DeleteMapping("/{idCliente}")
    public ResponseEntity<Void> deletar(@PathVariable(name = "idCliente")
                                        Integer idUsuario) throws RegraDeNegocioException {
        clienteService.deletar(idUsuario);
        log.info("Cliente deletado com sucesso");
        return ResponseEntity.noContent().build();
    }
}
