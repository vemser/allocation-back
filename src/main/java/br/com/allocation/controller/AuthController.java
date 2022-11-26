package br.com.allocation.controller;

import br.com.allocation.dto.loginDTO.LoginDTO;
import br.com.allocation.dto.usuarioDTO.UsuarioCreateDTO;
import br.com.allocation.dto.usuarioDTO.UsuarioDTO;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.security.TokenService;
import br.com.allocation.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<String> auth(@RequestBody @Valid LoginDTO loginDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(tokenService.autenticarAcesso(loginDTO, authenticationManager), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioDTO> create(@RequestBody @Valid UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {
        return ResponseEntity.ok(usuarioService.create(usuarioCreateDTO));
    }
//    @GetMapping("/logged")
//    public ResponseEntity<LoginWithIdDTO> loggedVerify() throws RegraDeNegocioException {
//        return new ResponseEntity<>(usuarioLoginService.getLoggedUser(), HttpStatus.OK);
//    }


}
