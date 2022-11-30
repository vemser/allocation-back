package br.com.allocation.controller;

import br.com.allocation.controller.interfaces.AuthInterfaceController;
import br.com.allocation.dto.loginDTO.LoginDTO;
import br.com.allocation.dto.loginDTO.LoginWithIdDTO;
import br.com.allocation.dto.usuarioDTO.MensagemDTO;
import br.com.allocation.dto.usuarioDTO.UsuarioCreateDTO;
import br.com.allocation.dto.usuarioDTO.UsuarioDTO;
import br.com.allocation.enums.Cargos;
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
import org.springframework.web.multipart.MultipartFile;

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

        return ResponseEntity.ok(usuarioService.create(usuarioCreateDTO, cargo));
    }

    @Override
    public ResponseEntity<MensagemDTO> uploadFile(@RequestParam("file") MultipartFile file,
                                                  @RequestParam("email") String email) {
        String message = "";
        try {
            fileService.store(file, email);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new MensagemDTO(message));
        } catch (Exception e) {
            e.printStackTrace();
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MensagemDTO(message));
        }
    }


}
