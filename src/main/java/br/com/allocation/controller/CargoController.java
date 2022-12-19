package br.com.allocation.controller;

import br.com.allocation.controller.interfaces.CargoInterfaceController;
import br.com.allocation.dto.usuariodto.UsuarioCargosDTO;
import br.com.allocation.dto.usuariodto.UsuarioDTO;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/cargo")
public class CargoController implements CargoInterfaceController {

    private final UsuarioService usuarioService;

    @Override
    public ResponseEntity<UsuarioDTO> atualizarCargo(@RequestBody @Valid UsuarioCargosDTO usuarioCargosDTO) throws RegraDeNegocioException {
        log.info("Atualizando . . .");
        return ResponseEntity.ok(usuarioService.atualizarCargo(usuarioCargosDTO));
    }
}
