package br.com.allocation.controller;

import br.com.allocation.controller.interfaces.AlunoInterfaceController;
import br.com.allocation.dto.AlunoDTO.AlunoCreateDTO;
import br.com.allocation.dto.AlunoDTO.AlunoDTO;
import br.com.allocation.dto.pageDTO.PageDTO;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.service.AlunoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/aluno")
@Validated
@RequiredArgsConstructor
@Slf4j
public class AlunoController implements AlunoInterfaceController {

    private final AlunoService alunoService;

    @PostMapping
    public ResponseEntity<AlunoDTO> salvar(@Valid @RequestBody AlunoCreateDTO alunoCreate) {
        log.info("Adicionando Aluno...");
        AlunoDTO alunoDTO = alunoService.salvar(alunoCreate);
        log.info("Aluno adicionado com sucesso!");
        return new ResponseEntity<>(alunoDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PageDTO<AlunoDTO>> listar(Integer pagina, Integer tamanho) {
        return ResponseEntity.ok(alunoService.listar(pagina, tamanho));
    }

    @PostMapping("/{id}")
    public ResponseEntity<AlunoDTO> editar(@Valid @RequestBody AlunoCreateDTO alunoCreate,
                                           @PathVariable(name = "id") Integer id) throws RegraDeNegocioException {
        log.info("Editando o Aluno...");
        AlunoDTO alunoDTO = alunoService.editar(id, alunoCreate);
        log.info("Aluno editado com sucesso!");
        return new ResponseEntity<>(alunoDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{idAluno}")
    public ResponseEntity<Void> deletar(@PathVariable(name = "idAluno")
                                        Integer id) throws RegraDeNegocioException {
        alunoService.deletar(id);
        log.info("Aluno deletado com sucesso");
        return ResponseEntity.noContent().build();
    }

}
