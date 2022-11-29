package br.com.allocation.service;

import br.com.allocation.dto.reservaAlocacaoDTO.ReservaAlocacaoCreateDTO;
import br.com.allocation.dto.reservaAlocacaoDTO.ReservaAlocacaoDTO;
import br.com.allocation.entity.AlunoEntity;
import br.com.allocation.entity.AvaliacaoEntity;
import br.com.allocation.entity.ReservaAlocacaoEntity;
import br.com.allocation.entity.VagaEntity;
import br.com.allocation.enums.StatusAluno;
import br.com.allocation.exceptions.RegraDeNegocioException;
import br.com.allocation.repository.AlunoRepository;
import br.com.allocation.repository.AvaliacaoRepository;
import br.com.allocation.repository.ReservaAlocacaoRepository;
import br.com.allocation.repository.VagaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservaAlocacaoService {
    private final ReservaAlocacaoRepository reservaAlocacaoRepository;
    private final AlunoRepository alunoRepository;
    //private final ObjectMapper objectMapper;

    private final VagaRepository vagaRepository;
    private final AvaliacaoRepository avaliacaoRepository;

    public ReservaAlocacaoDTO salvar(ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO) throws RegraDeNegocioException {

        ReservaAlocacaoEntity reservaAlocacaoEntity = converterEntity(reservaAlocacaoCreateDTO);
        if (reservaAlocacaoCreateDTO.getStatusAluno() == StatusAluno.RESERVADO) {
            reservaAlocacaoEntity.setDataReserva(reservaAlocacaoCreateDTO.getData());
        } else if (reservaAlocacaoCreateDTO.getStatusAluno() == StatusAluno.ALOCADO) {
            reservaAlocacaoEntity.setDataAlocacao(reservaAlocacaoCreateDTO.getData());
        } else if (reservaAlocacaoCreateDTO.getStatusAluno() == StatusAluno.DESALOCADO) {
            reservaAlocacaoEntity.setDataCancelamento(reservaAlocacaoCreateDTO.getData());
        }
        ReservaAlocacaoEntity saveAlocacao = reservaAlocacaoRepository.save(reservaAlocacaoEntity);
        return converterEmDTO(saveAlocacao);
    }

    public ReservaAlocacaoEntity findById(Integer id) throws RegraDeNegocioException {
        ReservaAlocacaoEntity alocacaoEntity = reservaAlocacaoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Reserva não encontrada!"));
        return alocacaoEntity;
    }

    private ReservaAlocacaoEntity converterEntity(ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO) {
        Optional<AlunoEntity> alunoEntity = alunoRepository.findById(reservaAlocacaoCreateDTO.getIdAluno());
        Optional<VagaEntity> vagaEntity = vagaRepository.findById(reservaAlocacaoCreateDTO.getIdVaga());
        Optional<AvaliacaoEntity> avaliacaoEntity = avaliacaoRepository.findById(reservaAlocacaoCreateDTO.getIdAvaliacao());
        ReservaAlocacaoEntity reservaAlocacaoEntity = new ReservaAlocacaoEntity(null,
                reservaAlocacaoCreateDTO.getDescricao(), null, null, null, null,
                reservaAlocacaoCreateDTO.getStatusAluno(),
                alunoEntity.get(), vagaEntity.get(), avaliacaoEntity.get());

        return reservaAlocacaoEntity;
    }

    private ReservaAlocacaoDTO converterEmDTO(ReservaAlocacaoEntity reservaAlocacaoEntity) {
        ReservaAlocacaoDTO reservaAlocacaoDTO = new ReservaAlocacaoDTO(reservaAlocacaoEntity.getCodigo(), reservaAlocacaoEntity.getVaga(),
                reservaAlocacaoEntity.getAluno(),
                reservaAlocacaoEntity.getAvaliacao(),
                reservaAlocacaoEntity.getDataReserva(),
                reservaAlocacaoEntity.getStatusAluno());

        return null;
    }

}
