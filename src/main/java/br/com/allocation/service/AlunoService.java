package br.com.allocation.service;

import br.com.allocation.dto.alunoDTO.AlunoCreateDTO;
import br.com.allocation.dto.alunoDTO.AlunoDTO;
import br.com.allocation.dto.pageDTO.PageDTO;
import br.com.allocation.dto.reservaAlocacaoDTO.ReservaAlocacaoCreateDTO;
import br.com.allocation.dto.tecnologiaDTO.TecnologiaCreateDTO;
import br.com.allocation.entity.AlunoEntity;
import br.com.allocation.entity.ProgramaEntity;
import br.com.allocation.enums.SituacaoAllocation;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.repository.AlunoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
        for (var tecnologia : alunoCreate.getTecnologias()) {
            if (tecnologiaService.findByName(tecnologia) == null) {
                TecnologiaCreateDTO tecnologiaCreateDTO = new TecnologiaCreateDTO();
                tecnologiaCreateDTO.setNome(tecnologia);
                tecnologiaService.create(tecnologiaCreateDTO);
            }
        }

        alunoEntity.setTecnologias(tecnologiaService.findBySet(alunoCreate.getTecnologias()));
        alunoEntity.setSituacaoAllocation(SituacaoAllocation.DISPONIVEL);
        alunoEntity = alunoRepository.save(alunoEntity);
        return converterEmDTO(alunoEntity);
    }

    private AlunoEntity converterEntity(AlunoCreateDTO alunoCreateDTO) {
        return objectMapper.convertValue(alunoCreateDTO, AlunoEntity.class);
    }

    public AlunoDTO editar(Integer idAluno, AlunoCreateDTO alunoCreateDTO) throws RegraDeNegocioException {
        this.findById(idAluno);

        AlunoEntity alunoEntity = converterEntity(alunoCreateDTO);
        ProgramaEntity programa = programaService.findById(alunoCreateDTO.getIdPrograma());
        alunoEntity.setTecnologias(tecnologiaService.findBySet(alunoCreateDTO.getTecnologias()));
        alunoEntity.setPrograma(programa);
        alunoEntity.setIdAluno(idAluno);

        return converterEmDTO(alunoRepository.save(alunoEntity));
    }

    public PageDTO<AlunoDTO> listar(Integer pagina, Integer tamanho) {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<AlunoEntity> paginaRepository = alunoRepository.findAll(pageRequest);

        List<AlunoDTO> alunoDTOList = paginaRepository.getContent().stream()
                .map(this::converterEmDTO)
                .toList();

        return new PageDTO<>(paginaRepository.getTotalElements(),
                paginaRepository.getTotalPages(),
                pagina,
                tamanho,
                alunoDTOList);
    }

    public PageDTO<AlunoDTO> listarPorNome(Integer pagina, Integer tamanho, String nome) {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<AlunoEntity> paginaRepository = alunoRepository.findAllByNomeContainingIgnoreCase(pageRequest, nome);

        List<AlunoDTO> alunoDTOList = paginaRepository.getContent().stream()
                .map(this::converterEmDTO)
                .collect(Collectors.toList());

        return new PageDTO<>(paginaRepository.getTotalElements(),
                paginaRepository.getTotalPages(),
                pagina,
                tamanho,
                alunoDTOList);
    }

    public PageDTO<AlunoDTO> listarPorEmail(Integer pagina, Integer tamanho, String email) {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<AlunoEntity> paginaRepository = alunoRepository.findAllByEmailIgnoreCase(pageRequest, email);

        List<AlunoDTO> alunoDTOList = paginaRepository.getContent().stream()
                .map(this::converterEmDTO)
                .collect(Collectors.toList());

        return new PageDTO<>(paginaRepository.getTotalElements(),
                paginaRepository.getTotalPages(),
                pagina,
                tamanho,
                alunoDTOList);
    }

    public PageDTO<AlunoDTO> listarDisponiveis(Integer pagina, Integer tamanho) {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<AlunoEntity> paginaRepository = alunoRepository.findAllBySituacaoAllocation(pageRequest, SituacaoAllocation.DISPONIVEL);

        List<AlunoDTO> alunoDTOList = paginaRepository.getContent().stream()
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

    public AlunoDTO converterEmDTO(AlunoEntity alunoEntity) {
        String emProcesso = alunoEntity.getSituacaoAllocation().equals(SituacaoAllocation.DISPONIVEL)? "não":"sim";

        List<String> tecs = new ArrayList<>();

        alunoEntity.getTecnologias()
                .forEach(entity -> tecs.add(entity.getNome()));

        return new AlunoDTO(
                alunoEntity.getNome(),
                alunoEntity.getEmail(),
                alunoEntity.getPrograma().getIdPrograma(),
                alunoEntity.getArea(),
                alunoEntity.getCidade(),
                alunoEntity.getEstado(),
                alunoEntity.getTelefone(),
                alunoEntity.getDescricao(),
                alunoEntity.getSituacao(),
                tecs,
                alunoEntity.getIdAluno(),
                emProcesso);
    }

    public AlunoEntity findByEmail(String email) throws RegraDeNegocioException {
        return alunoRepository.findByEmailIgnoreCase(email).orElseThrow(() -> new RegraDeNegocioException("Aluno não encontrado!"));
    }

    public AlunoEntity alterarStatusAluno(Integer idAluno,
                                          ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO) throws RegraDeNegocioException {
        AlunoEntity alunoEntity = findById(idAluno);
        alunoEntity.setSituacaoAllocation(reservaAlocacaoCreateDTO.getSituacaoAllocation());
        return alunoRepository.save(alunoEntity);
    }

    public void alterarStatusAlunoCancelado(Integer idAluno,
                                            ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO) throws RegraDeNegocioException {
        AlunoEntity alunoEntity = findById(idAluno);
        if (reservaAlocacaoCreateDTO.getSituacaoAllocation().equals(SituacaoAllocation.ALOCADO)
                || reservaAlocacaoCreateDTO.getSituacaoAllocation().equals(SituacaoAllocation.RESERVADO)
                || reservaAlocacaoCreateDTO.getSituacaoAllocation().equals(SituacaoAllocation.FINALIZADO)) {
            alunoEntity.setSituacaoAllocation(SituacaoAllocation.DISPONIVEL);
        }
        alunoRepository.save(alunoEntity);
    }

    public void verificarDisponibilidadeAluno(AlunoEntity alunoEntity,
                                              ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO) throws RegraDeNegocioException {

        if (alunoEntity.getSituacaoAllocation().equals(SituacaoAllocation.ALOCADO)) {
            if (!reservaAlocacaoCreateDTO.getSituacaoAllocation().equals(SituacaoAllocation.DISPONIVEL)) {
                throw new RegraDeNegocioException("Aluno não está disponivel!");
            }
        } else if (alunoEntity.getSituacaoAllocation().equals(SituacaoAllocation.RESERVADO)) {
            if (reservaAlocacaoCreateDTO.getSituacaoAllocation().equals(SituacaoAllocation.RESERVADO)) {
                throw new RegraDeNegocioException("Aluno não está disponivel!");
            }
        }
    }


}
