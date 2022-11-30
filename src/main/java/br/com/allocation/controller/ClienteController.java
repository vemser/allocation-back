package br.com.allocation.controller;

import br.com.allocation.controller.interfaces.ClienteInterfaceController;
import br.com.allocation.dto.clienteDTO.ClienteCreateDTO;
import br.com.allocation.dto.clienteDTO.ClienteDTO;
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

    @Override
    public ResponseEntity<ClienteDTO> salvar(@Valid @RequestBody ClienteCreateDTO clienteCreate) {
        log.info("Adicionando o Usuário...");
        ClienteDTO cliente = clienteService.salvar(clienteCreate);
        log.info("Usuário adicionado com sucesso!");
        return new ResponseEntity<>(cliente, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<PageDTO<ClienteDTO>> listar(Integer pagina, Integer tamanho) {
        return ResponseEntity.ok(clienteService.listar(pagina, tamanho));
    }

    @Override
    public ResponseEntity<ClienteDTO> editar(@Valid @RequestBody ClienteCreateDTO clienteCreate, Integer idCliente) throws RegraDeNegocioException {
        log.info("Editando o Cliente...");
        ClienteDTO cliente = clienteService.editar(idCliente, clienteCreate);
        log.info("Cliente editado com sucesso!");
        return new ResponseEntity<>(cliente, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deletar(Integer idUsuario) throws RegraDeNegocioException {
        clienteService.deletar(idUsuario);
        log.info("Cliente deletado com sucesso");
        return ResponseEntity.noContent().build();
    }
}
