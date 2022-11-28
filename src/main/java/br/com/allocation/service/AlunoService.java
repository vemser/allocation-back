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
import br.com.allocation.repository.ProgramaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AlunoService {
    private final AlunoRepository alunoRepository;
    private final ObjectMapper objectMapper;

    private final TecnologiaService tecnologiaService;
    private final ProgramaRepository programaRepository;

    public AlunoDTO salvar(AlunoCreateDTO alunoCreate) {

        AlunoEntity alunoEntity = converterEntity(alunoCreate);
        Optional<ProgramaEntity> programa = programaRepository.findById(alunoEntity.getArea().getValue());
        alunoEntity.setPrograma(programa.get());
        alunoEntity.setTecnologiaEntities(tecnologiaService.findBySet(alunoCreate.getTecnologias()));
        alunoEntity.setStatusAluno(StatusAluno.DISPONIVEL);
        alunoRepository.save(alunoEntity);
        AlunoDTO alunoDTO = converterEmDTO(alunoEntity);
        return alunoDTO;
    }

    private AlunoEntity converterEntity(AlunoCreateDTO alunoCreateDTO) {

        return objectMapper.convertValue(alunoCreateDTO, AlunoEntity.class);
    }


    public AlunoDTO converterEmDTO(AlunoEntity alunoEntity) {
        String emProcesso;
        if (alunoEntity.getReservaAlocacao().getSituacao().equals("ATIVO")) {
            emProcesso = "Sim";
        } else {
            emProcesso = "Não";
        }
        Set<TecnologiaDTO> tecnologiaDTOS = alunoEntity.getTecnologiaEntities()
                .stream()
                .map(tecnologiaEntity -> tecnologiaService.converterEmDTO(tecnologiaEntity))
                .collect(Collectors.toSet());

        AlunoDTO alunoDTO = new AlunoDTO(alunoEntity.getNome(),
                alunoEntity.getArea(),
                tecnologiaDTOS,
                alunoEntity.getPrograma().getNome(),
                emProcesso,
                alunoEntity.getStatusAluno());
        return alunoDTO;
    }

    public AlunoDTO editar(Integer id, AlunoCreateDTO alunoCreateDTO) throws RegraDeNegocioException {
        this.findById(id);
        AlunoEntity alunoEntity = converterEntity(alunoCreateDTO);
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
        this.findById(id);
        alunoRepository.deleteById(id);
    }

    public List<AlunoDTO> disponiveis() {
        return alunoRepository.findAllByStatusAluno(StatusAluno.DISPONIVEL)
                .stream()
                .map(this::converterEmDTO)
                .collect(Collectors.toList());
    }

}
