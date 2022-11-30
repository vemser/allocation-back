package br.com.allocation.service;

import br.com.allocation.dto.alunoDTO.AlunoCreateDTO;
import br.com.allocation.dto.alunoDTO.AlunoDTO;
import br.com.allocation.dto.pageDTO.PageDTO;
import br.com.allocation.dto.tecnologiaDTO.TecnologiaDTO;
import br.com.allocation.entity.AlunoEntity;
import br.com.allocation.entity.ProgramaEntity;
import br.com.allocation.enums.StatusAluno;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.repository.AlunoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AlunoService {
    private final AlunoRepository alunoRepository;
    private final ObjectMapper objectMapper;
    private final TecnologiaService tecnologiaService;
    private final ProgramaService programaService;

    public AlunoDTO salvar(AlunoCreateDTO alunoCreate) throws RegraDeNegocioException {
        AlunoEntity alunoEntity = converterEntity(alunoCreate);

        ProgramaEntity programa = programaService.findById(alunoCreate.getIdPrograma());
        alunoEntity.setPrograma(programa);
        alunoEntity.setTecnologias(tecnologiaService.findBySet(alunoCreate.getTecnologias()));
        alunoEntity.setStatusAluno(StatusAluno.DISPONIVEL);
        alunoRepository.save(alunoEntity);
        return converterEmDTO(alunoEntity);
    }

    private AlunoEntity converterEntity(AlunoCreateDTO alunoCreateDTO) {
        return objectMapper.convertValue(alunoCreateDTO, AlunoEntity.class);
    }

    public AlunoDTO converterEmDTO(AlunoEntity alunoEntity) {
        String emProcesso = "Não";
        Set<TecnologiaDTO> tecnologiaDTOS = alunoEntity.getTecnologias()
                .stream()
                .map(tecnologiaService::converterEmDTO)
                .collect(Collectors.toSet());

        return new AlunoDTO(alunoEntity.getNome(),
                alunoEntity.getEmail(),
                alunoEntity.getArea(),
                tecnologiaDTOS,
                alunoEntity.getPrograma().getIdPrograma(),
                emProcesso,
                alunoEntity.getStatusAluno());
    }

    public AlunoDTO editar(Integer id, AlunoCreateDTO alunoCreateDTO) throws RegraDeNegocioException {
        this.findById(id);
        AlunoEntity alunoEntity = converterEntity(alunoCreateDTO);
        return converterEmDTO(alunoRepository.save(alunoEntity));
    }

    public PageDTO<AlunoDTO> listar(Integer pagina, Integer tamanho) {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<AlunoEntity> paginaRepository = alunoRepository.findAll(pageRequest);

        List<AlunoDTO> alunoDTOList =  paginaRepository.getContent().stream()
                .map(this::converterEmDTO)
                .collect(Collectors.toList());

        return new PageDTO<>(paginaRepository.getTotalElements(),
                paginaRepository.getTotalPages(),
                pagina,
                tamanho,
                alunoDTOList);
    }

    public AlunoEntity findById(Integer id) throws RegraDeNegocioException {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Aluno não encontrado"));
    }

    public void deletar(Integer id) throws RegraDeNegocioException {
        this.findById(id);
        alunoRepository.deleteById(id);
    }

    public List<AlunoDTO> disponiveis() {
        return alunoRepository.findAllByStatusAluno(StatusAluno.DISPONIVEL)
                .stream()
                .map(this::converterEmDTO)
                .collect(Collectors.toList());
    }

    public AlunoEntity findByEmail(String email) throws RegraDeNegocioException {
        return alunoRepository.findByEmail(email).orElseThrow(() -> new RegraDeNegocioException("Aluno não encontrado!"));
    }
    public void alterarDisponibilidadeAluno(Integer idAluno,StatusAluno statusAluno) throws RegraDeNegocioException {
        AlunoEntity alunoEntity = findById(idAluno);
        alunoEntity.setStatusAluno(statusAluno);
        alunoRepository.save(alunoEntity);
    }
}
