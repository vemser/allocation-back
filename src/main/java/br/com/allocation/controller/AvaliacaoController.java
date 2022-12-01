package br.com.allocation.controller;

import br.com.allocation.controller.interfaces.AvaliacaoInterfaceController;
import br.com.allocation.dto.avaliacaoDTO.AvaliacaoCreateDTO;
import br.com.allocation.dto.avaliacaoDTO.AvaliacaoDTO;
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

    @Override
    public ResponseEntity<AvaliacaoDTO> salvar(@Valid @RequestBody AvaliacaoCreateDTO avaliacaoCreateDTO) throws RegraDeNegocioException {
        log.info("Adicionando Avaliação...");
        AvaliacaoDTO avaliacaoDTO = avaliacaoService.salvar(avaliacaoCreateDTO);
        log.info("Avaliação adicionada com sucesso!");
        return new ResponseEntity<>(avaliacaoDTO, HttpStatus.CREATED);
    }
    @Override
    public ResponseEntity<PageDTO<AvaliacaoDTO>> listar(Integer pagina, Integer tamanho) {
        return ResponseEntity.ok(avaliacaoService.listar(pagina, tamanho));
    }

    @Override
    public ResponseEntity<AvaliacaoDTO> listarPorId(Integer idAvaliacao) throws RegraDeNegocioException {
        return ResponseEntity.ok(avaliacaoService.listarPorId(idAvaliacao));
    }

    @Override
    public ResponseEntity<AvaliacaoDTO> editar(@Valid @RequestBody AvaliacaoCreateDTO avaliacaoCreateDTO, Integer idAvaliacao) throws RegraDeNegocioException {
        log.info("Editando Avaliação...");
        AvaliacaoDTO avaliacaoDTO = avaliacaoService.editar(idAvaliacao, avaliacaoCreateDTO);
        log.info("Avalição editado com sucesso!");
        return new ResponseEntity<>(avaliacaoDTO, HttpStatus.CREATED);
    }
    @Override
    public ResponseEntity<Void> deletar(Integer idAvaliacao) throws RegraDeNegocioException {
        avaliacaoService.deletar(idAvaliacao);
        log.info("Avaliação deletada com sucesso");
        return ResponseEntity.noContent().build();
    }
}
