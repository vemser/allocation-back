package br.com.allocation.controller;

import br.com.allocation.controller.interfaces.ProgramaInterfaceController;
import br.com.allocation.dto.programaDTO.ProgramaCreateDTO;
import br.com.allocation.dto.programaDTO.ProgramaDTO;
import br.com.allocation.dto.pageDTO.PageDTO;
import br.com.allocation.enums.Situacao;
import br.com.allocation.enums.SituacaoPrograma;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.service.ProgramaService;
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

    @Override
    @PostMapping
    public ResponseEntity<ProgramaDTO> salvar(@RequestParam("situacao") SituacaoPrograma situacao,
            @Valid @RequestBody ProgramaCreateDTO programaCreate) {
        log.info("Adicionando o Usuário...");
        ProgramaDTO programa = programaService.salvar(programaCreate, situacao);
        log.info("Usuário adicionado com sucesso!");
        return new ResponseEntity<>(programa, HttpStatus.CREATED);
    }

    @Override
    @GetMapping
    public ResponseEntity<PageDTO<ProgramaDTO>> listar(Integer pagina, Integer tamanho){
        return ResponseEntity.ok(programaService.listar(pagina, tamanho));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ProgramaDTO> editar(@RequestParam("situacao") SituacaoPrograma situacao,
            @Valid @RequestBody ProgramaCreateDTO programaCreate,
                                              @PathVariable(name = "id") Integer id) throws RegraDeNegocioException {
        log.info("Editando o Programa...");
        ProgramaDTO programa = programaService.editar(id, programaCreate, situacao);
        log.info("Programa editado com sucesso!");
        return new ResponseEntity<>(programa, HttpStatus.CREATED);
    }

    @Override
    @DeleteMapping("/{idPrograma}")
    public ResponseEntity<Void> deletar(@PathVariable(name = "idPrograma") Integer idUsuario) throws RegraDeNegocioException {
        programaService.deletar(idUsuario);
        log.info("Programa deletado com sucesso");
        return ResponseEntity.noContent().build();
    }
}
