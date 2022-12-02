package br.com.allocation.controller;

import br.com.allocation.controller.interfaces.AlunoInterfaceController;
import br.com.allocation.dto.alunoDTO.AlunoCreateDTO;
import br.com.allocation.dto.alunoDTO.AlunoDTO;
import br.com.allocation.dto.pageDTO.PageDTO;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.service.AlunoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/aluno")
@Validated
@RequiredArgsConstructor
@Slf4j
public class AlunoController implements AlunoInterfaceController {

    private final AlunoService alunoService;

    @Override
    public ResponseEntity<AlunoDTO> salvar(@Valid @RequestBody AlunoCreateDTO alunoCreate) throws RegraDeNegocioException {
        log.info("Adicionando Aluno...");
        AlunoDTO alunoDTO = alunoService.salvar(alunoCreate);
        log.info("Aluno adicionado com sucesso!");
        return new ResponseEntity<>(alunoDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<PageDTO<AlunoDTO>> listar(Integer pagina, Integer tamanho) {
        return ResponseEntity.ok(alunoService.listar(pagina, tamanho));
    }

    @Override
    public ResponseEntity<PageDTO<AlunoDTO>> listarPorEmail(Integer pagina, Integer tamanho, String email) {
        return ResponseEntity.ok(alunoService.listarPorEmail(pagina, tamanho, email));
    }

    @Override
    public ResponseEntity<PageDTO<AlunoDTO>> listarPorNome(Integer pagina, Integer tamanho, String nome) {
        return ResponseEntity.ok(alunoService.listarPorNome(pagina, tamanho, nome));
    }

    @Override
    public ResponseEntity<AlunoDTO> editar(@Valid @RequestBody AlunoCreateDTO alunoCreate,
                                           @PathVariable(name = "idAluno") Integer idAluno) throws RegraDeNegocioException {
        log.info("Editando o Aluno...");
        AlunoDTO alunoDTO = alunoService.editar(idAluno, alunoCreate);
        log.info("Aluno editado com sucesso!");
        return new ResponseEntity<>(alunoDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deletar(@PathVariable(name = "idAluno")
                                        Integer idAluno) throws RegraDeNegocioException {
        alunoService.deletar(idAluno);
        log.info("Aluno deletado com sucesso");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<PageDTO<AlunoDTO>> disponiveis(Integer pagina, Integer tamanho) {
        return ResponseEntity.ok(alunoService.listarDisponiveis(pagina, tamanho));
    }

}
