package br.com.allocation.service;

import br.com.allocation.dto.avaliacaoDTO.AvaliacaoCreateDTO;
import br.com.allocation.dto.avaliacaoDTO.AvaliacaoDTO;
import br.com.allocation.dto.pageDTO.PageDTO;
import br.com.allocation.entity.AvaliacaoEntity;
import br.com.allocation.enums.Situacao;
import br.com.allocation.enums.SituacaoAluno;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.repository.AvaliacaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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

        avaliacaoEntity = avaliacaoRepository.save(avaliacaoEntity);
        return converterEmDTO(avaliacaoEntity);
    }

    public AvaliacaoDTO editar(Integer id, AvaliacaoCreateDTO avaliacaoCreateDTO) throws RegraDeNegocioException {
        AvaliacaoEntity avaliacaoEntity = findById(id);
        avaliacaoEntity = converterEntity(avaliacaoCreateDTO);
        avaliacaoEntity = avaliacaoRepository.save(avaliacaoEntity);
        return converterEmDTO(avaliacaoEntity);
    }

    public void deletar(Integer codigoVaga) throws RegraDeNegocioException {
        AvaliacaoEntity avaliacaoEntity = findById(codigoVaga);
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

    public AvaliacaoDTO listarPorId(Integer idAvaliacao) throws RegraDeNegocioException {
        return converterEmDTO(findById(idAvaliacao));
    }

    public AvaliacaoEntity findById(Integer id) throws RegraDeNegocioException {
        return avaliacaoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Avaliação não encontrada!"));
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
