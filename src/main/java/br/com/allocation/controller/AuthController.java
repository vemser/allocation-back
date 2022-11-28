package br.com.allocation.controller;

import br.com.allocation.controller.interfaces.AuthInterfaceController;
import br.com.allocation.dto.loginDTO.LoginDTO;
import br.com.allocation.dto.loginDTO.LoginWithIdDTO;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.security.TokenService;
import br.com.allocation.service.EmailService;
import br.com.allocation.service.FileService;
import br.com.allocation.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
public class AuthController implements AuthInterfaceController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UsuarioService usuarioService;
    private  final FileService fileService;
    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<String> auth(@RequestBody @Valid LoginDTO loginDTO) {
        return new ResponseEntity<>(tokenService.autenticarAcesso(loginDTO, authenticationManager), HttpStatus.OK);
    }

    @GetMapping("/logged")
    public ResponseEntity<LoginWithIdDTO> loggedVerify() throws RegraDeNegocioException {
        
        return new ResponseEntity<>(usuarioService.getLoggedUser(), HttpStatus.OK);
    }


}
