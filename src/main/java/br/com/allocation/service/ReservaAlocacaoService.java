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

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservaAlocacaoService {
    private final ReservaAlocacaoRepository reservaAlocacaoRepository;
    private final AlunoRepository alunoRepository;
    private final ObjectMapper objectMapper;

    private final VagaRepository vagaRepository;
    private final AvaliacaoRepository avaliacaoRepository;
    public ReservaAlocacaoDTO salvar(ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO) throws RegraDeNegocioException {


        ReservaAlocacaoEntity reservaAlocacaoEntity = converterEntity(reservaAlocacaoCreateDTO);
        ReservaAlocacaoEntity saveAlocacao = reservaAlocacaoRepository.save(reservaAlocacaoEntity);
        return converterEmDTO(saveAlocacao);
    }

    public ReservaAlocacaoEntity findById(Integer id) throws RegraDeNegocioException {
        ReservaAlocacaoEntity alocacaoEntity = reservaAlocacaoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Reserva não encontrada!"));
        return alocacaoEntity;
    }

    private ReservaAlocacaoEntity converterEntity(ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO) {
        LocalDate data;
        if(reservaAlocacaoCreateDTO.getStatusAluno() == StatusAluno.RESERVADO){
            reservaAlocacaoEntity.setDataReserva(LocalDate.now());
        } else if (reservaAlocacaoCreateDTO.getStatusAluno() == StatusAluno.ALOCADO) {
            reservaAlocacaoEntity.setDataAlocacao(LocalDate.now());
        } else if (reservaAlocacaoCreateDTO.getStatusAluno() == StatusAluno.CANCElADO) {
            reservaAlocacaoEntity.setDataCancelamento(LocalDate.now());
        }

        Optional<AlunoEntity> alunoEntity = alunoRepository.findByNome(reservaAlocacaoCreateDTO.getAluno().getNome());
        Optional<VagaEntity> vagaEntity = vagaRepository.findByNome(reservaAlocacaoCreateDTO.getVaga().getNome());
        Optional<AvaliacaoEntity> avaliacaoEntity = avaliacaoRepository.findByNome(reservaAlocacaoCreateDTO.getVaga().getNome());
        ReservaAlocacaoEntity reservaAlocacaoEntity = new ReservaAlocacaoEntity(reservaAlocacaoCreateDTO.getCodigo(),
                reservaAlocacaoCreateDTO.getDescricao(),
                )
        return objectMapper.convertValue(reservaAlocacaoCreateDTO, ReservaAlocacaoEntity.class);
    }

    private ReservaAlocacaoDTO converterEmDTO(ReservaAlocacaoEntity reservaAlocacaoEntity) {
        return objectMapper.convertValue(reservaAlocacaoEntity, ReservaAlocacaoDTO.class);
    }

}
