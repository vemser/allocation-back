package br.com.allocation.controller;

import br.com.allocation.controller.interfaces.TecnologiaInterfaceController;
import br.com.allocation.dto.pagedto.PageDTO;
import br.com.allocation.dto.tecnologiadto.TecnologiaDTO;
import br.com.allocation.service.TecnologiaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tecnologia")
@Validated
@RequiredArgsConstructor
@Slf4j
public class TecnologiaController implements TecnologiaInterfaceController {
    private final TecnologiaService tecnologiaService;

    @GetMapping("/tecnologia-busca")
    public PageDTO<TecnologiaDTO> buscar(@RequestParam String nomeTecnologia,
                                         @RequestParam int page,
                                         @RequestParam int size) {
        return tecnologiaService.buscarPorTecnologia(nomeTecnologia, PageRequest.of(page, size));
    }
}
