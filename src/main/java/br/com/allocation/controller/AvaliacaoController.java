package br.com.allocation.controller;

import br.com.allocation.controller.interfaces.AvaliacaoInterfaceController;
import br.com.allocation.dto.Avaliacao.AvaliacaoCreateDTO;
import br.com.allocation.dto.Avaliacao.AvaliacaoDTO;
import br.com.allocation.dto.pageDTO.PageDTO;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.service.AvaliacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/avaliacao")
@Validated
@RequiredArgsConstructor
@Slf4j
public class AvaliacaoController implements AvaliacaoInterfaceController {
    private final AvaliacaoService avaliacaoService;

    @PostMapping
    public ResponseEntity<AvaliacaoDTO> salvar(@Valid @RequestBody AvaliacaoCreateDTO avaliacaoCreateDTO) {
        log.info("Adicionando Avaliação...");
        AvaliacaoDTO avaliacaoDTO = avaliacaoService.salvar(avaliacaoCreateDTO);
        log.info("Avaliação adicionada com sucesso!");
        return new ResponseEntity<>(avaliacaoDTO, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<PageDTO<AvaliacaoDTO>> listar(Integer pagina, Integer tamanho) {
        return ResponseEntity.ok(avaliacaoService.listar(pagina, tamanho));
    }
    @PostMapping("/{id}")
    public ResponseEntity<AvaliacaoDTO> editar(@Valid @RequestBody AvaliacaoCreateDTO avaliacaoCreateDTO,
                                           @PathVariable(name = "id") Integer id) throws RegraDeNegocioException {
        log.info("Editando Avaliação...");
        AvaliacaoDTO avaliacaoDTO = avaliacaoService.editar(id, avaliacaoCreateDTO);
        log.info("Avalição editado com sucesso!");
        return new ResponseEntity<>(avaliacaoDTO, HttpStatus.CREATED);
    }
    @DeleteMapping("/{idAvaliacao}")
    public ResponseEntity<Void> deletar(@PathVariable(name = "idAvaliacao")
                                        Integer id) throws RegraDeNegocioException {
        avaliacaoService.deletar(id);
        log.info("Avaliação deletada com sucesso");
        return ResponseEntity.noContent().build();
    }
}
