package br.com.allocation.service;

import br.com.allocation.dto.AlunoDTO.AlunoCreateDTO;
import br.com.allocation.dto.AlunoDTO.AlunoDTO;
import br.com.allocation.dto.pageDTO.PageDTO;
import br.com.allocation.entity.AlunoEntity;
import br.com.allocation.entity.ClienteEntity;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.repository.AlunoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AlunoService {
    private final AlunoRepository alunoRepository;
    private final ObjectMapper objectMapper;

    public AlunoDTO salvar(AlunoCreateDTO alunoCreate) {
        AlunoEntity alunoEntity = converterEntity(alunoCreate);
        alunoRepository.save(alunoEntity);
        AlunoDTO alunoDTO = converterEmDTO(alunoEntity);
        return alunoDTO;
    }

    private AlunoEntity converterEntity(AlunoCreateDTO alunoCreateDTO) {
        return objectMapper.convertValue(alunoCreateDTO, AlunoEntity.class);
    }

    private AlunoDTO converterEmDTO(AlunoEntity alunoEntity) {
        AlunoDTO alunoDTO = new AlunoDTO(alunoEntity.getNome(), alunoEntity.getArea(), alunoEntity.getEmail(),
                alunoEntity.getPrograma().getSituacao());
        return objectMapper.convertValue(alunoDTO, AlunoDTO.class);
    }

    public AlunoDTO editar(Integer id, AlunoCreateDTO alunoCreateDTO) throws RegraDeNegocioException {
        this.findById(id);
        AlunoEntity alunoEntity = converterEntity(alunoCreateDTO);
        alunoEntity.setIdAluno(id);
        AlunoDTO alunoDTO = converterEmDTO(alunoRepository.save(alunoEntity));
        return alunoDTO;

    }

    public PageDTO<AlunoDTO> listar(Integer pagina, Integer tamanho) {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<AlunoEntity> paginaRepository = alunoRepository.findAll(pageRequest);

        List<AlunoDTO> alunoDTOList = alunoRepository.findAll().stream()
                .map(this::converterEmDTO)
                .collect(Collectors.toList());

        return new PageDTO<>(paginaRepository.getTotalElements(),
                paginaRepository.getTotalPages(),
                pagina,
                tamanho,
                alunoDTOList);
    }

    private AlunoEntity findById(Integer id) throws RegraDeNegocioException {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Aluno não encontrado"));
    }

    public void deletar(Integer id) throws RegraDeNegocioException {
        AlunoDTO alunoDeletado = converterEmDTO(findById(id));
        alunoRepository.deleteById(id);
    }
}
