package br.com.allocation.controller;

import br.com.allocation.dto.reservaAlocacaoDTO.ReservaAlocacaoCreateDTO;
import br.com.allocation.dto.reservaAlocacaoDTO.ReservaAlocacaoDTO;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.service.ReservaAlocacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/reserva-alocacao")
@Validated
@RequiredArgsConstructor
@Slf4j
public class ReservaAlocacaoController {
    private final ReservaAlocacaoService reservaAlocacaoService;

    @PostMapping
    public ResponseEntity<ReservaAlocacaoDTO> salvar(@Valid @RequestBody ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO) throws RegraDeNegocioException {
        log.info("Adicionando a Reserva alocação...");
        ReservaAlocacaoDTO reservaAlocacaoDTO = reservaAlocacaoService.salvar(reservaAlocacaoCreateDTO);
        log.info("Reserva alocação adicionado com sucesso!");
        return new ResponseEntity<>(reservaAlocacaoDTO, HttpStatus.CREATED);
    }
}
