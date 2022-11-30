package br.com.allocation.controller;

import br.com.allocation.dto.usuarioDTO.UsuarioCargosDTO;
import br.com.allocation.dto.usuarioDTO.UsuarioDTO;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/cargo")
public class CargoController {

    private final UsuarioService usuarioService;

    @PutMapping("/atualizar")
    public ResponseEntity<UsuarioDTO> atualizarCargo(@RequestBody @Valid UsuarioCargosDTO usuarioCargosDTO) throws RegraDeNegocioException {
        log.info("Atualizando . . .");
        return ResponseEntity.ok(usuarioService.atualizarCargo(usuarioCargosDTO));
    }
}
