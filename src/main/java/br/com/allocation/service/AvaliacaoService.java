package br.com.allocation.service;

import br.com.allocation.dto.Avaliacao.AvaliacaoCreateDTO;
import br.com.allocation.dto.Avaliacao.AvaliacaoDTO;
import br.com.allocation.dto.pageDTO.PageDTO;
import br.com.allocation.entity.AvaliacaoEntity;
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
    private final ObjectMapper objectMapper;

    public AvaliacaoDTO salvar(AvaliacaoCreateDTO avaliacaoCreateDTO) {
        AvaliacaoEntity avaliacaoEntity = converterEntity(avaliacaoCreateDTO);
        return converterEmDTO(avaliacaoEntity);

    }
    public AvaliacaoEntity findById(Integer id) throws RegraDeNegocioException {
        return avaliacaoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Avaliação não encontrada"));
    }
    public AvaliacaoDTO editar(Integer id, AvaliacaoCreateDTO avaliacaoCreateDTO) throws RegraDeNegocioException {
        AvaliacaoEntity avaliacaoEntity = findById(id);
        avaliacaoEntity = converterEntity(avaliacaoCreateDTO);
        avaliacaoEntity = avaliacaoRepository.save(avaliacaoEntity);
        return converterEmDTO(avaliacaoEntity);
    }
    public void deletar(Integer idCliente) throws RegraDeNegocioException {
        AvaliacaoEntity avaliacaoEntity = findById(idCliente);
        avaliacaoRepository.delete(avaliacaoEntity);
    }
    public PageDTO<AvaliacaoDTO> listar(Integer pagina, Integer tamanho){
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

    private AvaliacaoEntity converterEntity(AvaliacaoCreateDTO avaliacaoCreateDTO) {
        return objectMapper.convertValue(avaliacaoCreateDTO, AvaliacaoEntity.class);
    }
    private AvaliacaoDTO converterEmDTO(AvaliacaoEntity avaliacaoEntity) {
        return objectMapper.convertValue(avaliacaoEntity, AvaliacaoDTO.class);
    }
}
