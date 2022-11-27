package br.com.allocation.service;

import br.com.allocation.dto.AlunoDTO.AlunoDTO;
import br.com.allocation.dto.pageDTO.PageDTO;
import br.com.allocation.dto.tecnologiaDTO.TecnologiaDTO;
import br.com.allocation.entity.TecnologiaEntity;
import br.com.allocation.repository.TecnologiaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TecnologiaService {
    private final TecnologiaRepository tecnologiaRepository;

    public PageDTO<TecnologiaDTO> buscar(String nomeTecnologia, PageRequest pageRequest){

        Page<TecnologiaEntity> tecnologiaEntities = tecnologiaRepository.findByNomeIsLikeIgnoreCase(nomeTecnologia, pageRequest);
        List<TecnologiaDTO> tecnologiaDTOS = tecnologiaEntities.getContent().stream().map(this::converterEmDTO).collect(Collectors.toList());

        return new PageDTO<>(tecnologiaEntities.getTotalElements(),
                tecnologiaEntities.getTotalPages(),
                pageRequest.getPageNumber(),
                pageRequest.getPageSize(),
                tecnologiaDTOS
        );
    }

    public TecnologiaDTO converterEmDTO(TecnologiaEntity tecnologiaEntity) {
        TecnologiaDTO tecnologiaDTO = new TecnologiaDTO(tecnologiaEntity.getNome());
        return tecnologiaDTO;
    }
}
