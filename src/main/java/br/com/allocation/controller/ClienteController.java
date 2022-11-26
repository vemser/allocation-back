package br.com.allocation.controller;

import br.com.allocation.controller.interfaces.ClienteInterfaceController;
import br.com.allocation.dto.ClienteDTO.ClienteCreateDTO;
import br.com.allocation.dto.ClienteDTO.ClienteDTO;
import br.com.allocation.dto.pageDTO.PageDTO;
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

    @PostMapping
    public ResponseEntity<ClienteDTO> salvar(@Valid @RequestBody ClienteCreateDTO clienteCreate) {
        log.info("Adicionando o Usuário...");
        ClienteDTO cliente = clienteService.salvar(clienteCreate);
        log.info("Usuário adicionado com sucesso!");
        return new ResponseEntity<>(cliente, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<PageDTO<ClienteDTO>> listar(Integer pagina, Integer tamanho) {
        return ResponseEntity.ok(clienteService.listar(pagina, tamanho));
    }
    @PostMapping("/{id}")
    public ResponseEntity<ClienteDTO> editar(@Valid @RequestBody ClienteCreateDTO clienteCreate,
                                             @PathVariable(name = "id") Integer id) throws RegraDeNegocioException {
        log.info("Editando o Cliente...");
        ClienteDTO cliente = clienteService.editar(id, clienteCreate);
        log.info("Cliente editado com sucesso!");
        return new ResponseEntity<>(cliente, HttpStatus.CREATED);
    }

    @DeleteMapping("/{idCliente}")
    public ResponseEntity<Void> deletar(@PathVariable(name = "idCliente")
                                        Integer idUsuario) throws RegraDeNegocioException {
        clienteService.deletar(idUsuario);
        log.info("Cliente deletado com sucesso");
        return ResponseEntity.noContent().build();
    }
}
