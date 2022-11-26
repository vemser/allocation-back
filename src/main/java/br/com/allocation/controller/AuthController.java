package br.com.allocation.controller;

import br.com.allocation.controller.entity.UsuarioEntity;
import br.com.allocation.dto.loginDTO.LoginDTO;

import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity<String> auth(@RequestBody @Valid LoginDTO loginDTO) throws RegraDeNegocioException {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getSenha()
                );

        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // UsuarioEntity
        Object principal = authenticate.getPrincipal();
        UsuarioEntity usuarioEntity = (UsuarioEntity) principal;

        String token = tokenService.getToken(usuarioEntity);
        return new ResponseEntity<>(token, HttpStatus.OK);


    }

//    @PostMapping("/register")
//    public ResponseEntity<UsuarioDTO> create(@RequestBody @Valid UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {
//        return ResponseEntity.ok(usuarioLoginService.create(usuarioCreateDTO));
//    }
//    @GetMapping("/logged")
//    public ResponseEntity<LoginWithIdDTO> loggedVerify() throws RegraDeNegocioException {
//        return new ResponseEntity<>(usuarioLoginService.getLoggedUser(), HttpStatus.OK);
//    }


}
