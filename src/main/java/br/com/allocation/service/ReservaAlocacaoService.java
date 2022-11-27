package br.com.allocation.service;

import br.com.allocation.dto.reservaAlocacaoDTO.ReservaAlocacaoCreateDTO;
import br.com.allocation.dto.reservaAlocacaoDTO.ReservaAlocacaoDTO;
import br.com.allocation.entity.ReservaAlocacaoEntity;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.repository.ReservaAlocacaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservaAlocacaoService {
    private final ReservaAlocacaoRepository reservaAlocacaoRepository;
    private final AlunoService alunoService;
    private final ObjectMapper objectMapper;

    public ReservaAlocacaoDTO salvar(ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO) throws RegraDeNegocioException {
        ReservaAlocacaoEntity reservaAlocacaoEntity = converterEntity(reservaAlocacaoCreateDTO);
        ReservaAlocacaoEntity saveAlocacao = reservaAlocacaoRepository.save(reservaAlocacaoEntity);
        return null;
    }

    public ReservaAlocacaoEntity findById(Integer id) throws RegraDeNegocioException {
        ReservaAlocacaoEntity alocacaoEntity = reservaAlocacaoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Reserva não encontrada!"));
        return alocacaoEntity;
    }

    private ReservaAlocacaoEntity converterEntity(ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO) {
        return objectMapper.convertValue(reservaAlocacaoCreateDTO, ReservaAlocacaoEntity.class);
    }

    private ReservaAlocacaoDTO converterEmDTO(ReservaAlocacaoEntity reservaAlocacaoEntity) {
        return objectMapper.convertValue(reservaAlocacaoEntity, ReservaAlocacaoDTO.class);
    }

}
