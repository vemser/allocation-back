package br.com.allocation.service;

import br.com.allocation.dto.alunodto.AlunoDTO;
import br.com.allocation.dto.avaliacaodto.AvaliacaoCreateDTO;
import br.com.allocation.dto.avaliacaodto.AvaliacaoDTO;
import br.com.allocation.dto.pagedto.PageDTO;
import br.com.allocation.entity.AlunoEntity;
import br.com.allocation.entity.AvaliacaoEntity;
import br.com.allocation.enums.SituacaoAluno;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.repository.AvaliacaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliacaoService {
    private final AvaliacaoRepository avaliacaoRepository;
    private final VagaService vagaService;
    private final AlunoService alunoService;
    private final ObjectMapper objectMapper;

    public AvaliacaoDTO salvar(AvaliacaoCreateDTO avaliacaoCreateDTO) throws RegraDeNegocioException {
        AvaliacaoEntity avaliacaoEntity = converterEntity(avaliacaoCreateDTO);
        avaliacaoEntity.setVaga(vagaService.findById(avaliacaoCreateDTO.getIdVaga()));
        avaliacaoEntity.setAluno(alunoService.findByEmail(avaliacaoCreateDTO.getEmailAluno()));
        avaliacaoEntity.setSituacao(SituacaoAluno.valueOf(avaliacaoCreateDTO.getSituacao()));
        avaliacaoEntity.setDataCriacao(LocalDate.now());
        avaliacaoEntity = avaliacaoRepository.save(avaliacaoEntity);
        return converterEmDTO(avaliacaoEntity);
    }

    public AvaliacaoDTO editar(Integer id, AvaliacaoCreateDTO avaliacaoCreateDTO) throws RegraDeNegocioException {
        AvaliacaoEntity avaliacaoEntity = findById(id);
        avaliacaoEntity = converterEntity(avaliacaoCreateDTO);
        avaliacaoEntity.setAluno(alunoService.findByEmail(avaliacaoCreateDTO.getEmailAluno()));
        avaliacaoEntity.setVaga(vagaService.findById(avaliacaoCreateDTO.getIdVaga()));
        avaliacaoEntity.setIdAvaliacao(id);
        avaliacaoEntity.setDataCriacao(LocalDate.now());
        avaliacaoEntity = avaliacaoRepository.save(avaliacaoEntity);
        return converterEmDTO(avaliacaoEntity);
    }

    public void deletar(Integer codigoVaga) throws RegraDeNegocioException {
        AvaliacaoEntity avaliacaoEntity = findById(codigoVaga);
        AlunoEntity alunoEntity = alunoService.findById(avaliacaoEntity.getIdAluno());
        alunoService.converterEmDTO(alunoEntity);
        avaliacaoRepository.delete(avaliacaoEntity);

    }

    public PageDTO<AvaliacaoDTO> listar(Integer pagina, Integer tamanho) {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<AvaliacaoEntity> paginaRepository = avaliacaoRepository.findAll(pageRequest);

        List<AvaliacaoDTO> avaliacaoDTOList = paginaRepository.getContent().stream()
                .map(this::converterEmDTO)
                .toList();

        return new PageDTO<>(paginaRepository.getTotalElements(),
                paginaRepository.getTotalPages(),
                pagina,
                tamanho,
                avaliacaoDTOList);
    }

    public PageDTO<AvaliacaoDTO> listarPorId(Integer idAvaliacao) throws RegraDeNegocioException {
        List<AvaliacaoDTO> list = List.of(converterEmDTO(findById(idAvaliacao)));
        Page<AvaliacaoDTO> page = new PageImpl<>(list);

        return new PageDTO<>(page.getTotalElements(),
                page.getTotalPages(),
                0,
                1,
                list
        );
    }

    public AvaliacaoEntity findById(Integer id) throws RegraDeNegocioException {
        return avaliacaoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Avalia????o n??o encontrada!"));
    }

    private AvaliacaoEntity converterEntity(AvaliacaoCreateDTO avaliacaoCreateDTO) {
        return objectMapper.convertValue(avaliacaoCreateDTO, AvaliacaoEntity.class);
    }

    public AvaliacaoDTO converterEmDTO(AvaliacaoEntity avaliacaoEntity) {
        AvaliacaoDTO dto = objectMapper.convertValue(avaliacaoEntity, AvaliacaoDTO.class);
        dto.setEmailAluno(avaliacaoEntity.getAluno().getEmail());
        dto.setIdVaga(avaliacaoEntity.getVaga().getIdVaga());
        return dto;
    }

}
