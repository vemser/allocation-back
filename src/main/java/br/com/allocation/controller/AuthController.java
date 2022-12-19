package br.com.allocation.controller;

import br.com.allocation.controller.interfaces.AuthInterfaceController;
import br.com.allocation.dto.logindto.LoginDTO;
import br.com.allocation.dto.logindto.LoginWithIdDTO;
import br.com.allocation.dto.usuariodto.FileDTO;
import br.com.allocation.dto.usuariodto.UsuarioCreateDTO;
import br.com.allocation.dto.usuariodto.UsuarioDTO;
import br.com.allocation.dto.usuariodto.UsuarioSenhaDTO;
import br.com.allocation.enums.Cargos;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.security.TokenService;
import br.com.allocation.service.FileService;
import br.com.allocation.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
public class AuthController implements AuthInterfaceController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UsuarioService usuarioService;
    private final FileService fileService;


    @Override
    public ResponseEntity<String> auth(@RequestBody @Valid LoginDTO loginDTO) {
        return new ResponseEntity<>(tokenService.autenticarAcesso(loginDTO, authenticationManager), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<LoginWithIdDTO> loggedVerify() throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.getLoggedUser(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UsuarioDTO> create(
            @RequestParam(required = false) Cargos cargo,
            @RequestBody @Valid UsuarioCreateDTO usuarioCreateDTO)
            throws RegraDeNegocioException {

        return new ResponseEntity<>(usuarioService.create(usuarioCreateDTO, cargo), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> recuperarSenha(String email) throws RegraDeNegocioException {
        return ResponseEntity.ok(usuarioService.recuperarSenha(email));
    }

    @Override
    public ResponseEntity<String> atualizarSenha(UsuarioSenhaDTO usuarioSenhaDTO, String token) throws RegraDeNegocioException {
        return ResponseEntity.ok(usuarioService.atualizarSenha(usuarioSenhaDTO, token));
    }

    @Override
    public ResponseEntity<FileDTO> uploadFile(@RequestParam("file") MultipartFile file,
                                              @RequestParam("email") String email) throws RegraDeNegocioException, IOException {
        return new ResponseEntity<>(fileService.store(file, email), HttpStatus.OK);
    }


}
