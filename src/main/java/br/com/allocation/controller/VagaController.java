package br.com.allocation.controller;

import br.com.allocation.controller.interfaces.VagaInterfaceController;
import br.com.allocation.dto.pageDTO.PageDTO;
import br.com.allocation.dto.vagaDTO.VagaCreateDTO;
import br.com.allocation.dto.vagaDTO.VagaDTO;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.service.RelatorioService;
import br.com.allocation.service.VagaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/vaga")
@Validated
@RequiredArgsConstructor
@Slf4j
public class VagaController implements VagaInterfaceController {

    private final VagaService vagaService;

    @Override
    public ResponseEntity<VagaDTO> salvar(VagaCreateDTO vagaCreateDTO) throws RegraDeNegocioException {
        log.info("Adicionando a vaga...");
        VagaDTO vaga = vagaService.salvar(vagaCreateDTO);
        log.info("Vaga adicionado com sucesso!");
        return new ResponseEntity<>(vaga, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<PageDTO<VagaDTO>> listar(Integer pagina, Integer tamanho) {
        return ResponseEntity.ok(vagaService.listar(pagina, tamanho));
    }

    @Override
    public ResponseEntity<VagaDTO> listarPoId(Integer idVaga) throws RegraDeNegocioException {
        return ResponseEntity.ok(vagaService.listarPorId(idVaga));
    }

    @Override
    public ResponseEntity<VagaDTO> editar(Integer idVaga, VagaCreateDTO vagaCreateDTO) throws RegraDeNegocioException {

        log.info("Editando a vaga...");
        VagaDTO vaga = vagaService.editar(idVaga, vagaCreateDTO);
        log.info("Vaga editada com sucesso!");
        return new ResponseEntity<>(vaga, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deletar(Integer idVaga) throws RegraDeNegocioException {
        vagaService.deletar(idVaga);
        log.info("Vaga deletado com sucesso");
        return ResponseEntity.noContent().build();
    }
}
