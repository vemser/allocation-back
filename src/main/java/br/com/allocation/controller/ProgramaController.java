package br.com.allocation.controller;

import br.com.allocation.controller.interfaces.ProgramaInterfaceController;
import br.com.allocation.dto.ProgramaDTO.ProgramaCreateDTO;
import br.com.allocation.dto.ProgramaDTO.ProgramaDTO;
import br.com.allocation.dto.pageDTO.PageDTO;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.service.ProgramaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/programa")
@Validated
@RequiredArgsConstructor
@Slf4j
public class ProgramaController implements ProgramaInterfaceController {

    private final ProgramaService programaService;


    @PostMapping
    public ResponseEntity<ProgramaDTO> salvar(@Valid @RequestBody ProgramaCreateDTO programaCreate) {
        log.info("Adicionando o Usuário...");
        ProgramaDTO programa = programaService.salvar(programaCreate);
        log.info("Usuário adicionado com sucesso!");
        return new ResponseEntity<>(programa, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<PageDTO<ProgramaDTO>> listar(Integer pagina, Integer tamanho){
        return ResponseEntity.ok(programaService.listar(pagina, tamanho));
    }


    @PostMapping("/{id}")
    public ResponseEntity<ProgramaDTO> editar(@Valid @RequestBody ProgramaCreateDTO programaCreate,
                                              @PathVariable(name = "id") Integer id) throws RegraDeNegocioException {
        log.info("Editando o Programa...");
        ProgramaDTO programa = programaService.editar(id, programaCreate);
        log.info("Programa editado com sucesso!");
        return new ResponseEntity<>(programa, HttpStatus.CREATED);
    }

    @DeleteMapping("/{idPrograma}")
    public ResponseEntity<Void> deletar(@PathVariable(name = "idPrograma") Integer idUsuario) throws RegraDeNegocioException {
        programaService.deletar(idUsuario);
        log.info("Programa deletado com sucesso");
        return ResponseEntity.noContent().build();
    }
}
