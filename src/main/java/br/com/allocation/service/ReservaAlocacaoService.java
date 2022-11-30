package br.com.allocation.service;

import br.com.allocation.dto.alunoDTO.AlunoDTO;
import br.com.allocation.dto.avaliacaoDTO.AvaliacaoDTO;
import br.com.allocation.dto.clienteDTO.ClienteDTO;
import br.com.allocation.dto.reservaAlocacaoDTO.ReservaAlocacaoCreateDTO;
import br.com.allocation.dto.reservaAlocacaoDTO.ReservaAlocacaoDTO;
import br.com.allocation.dto.vagaDTO.VagaDTO;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservaAlocacaoService {
    private final ReservaAlocacaoRepository reservaAlocacaoRepository;
    private final AlunoService alunoService;
    private final VagaService vagaService;
    private final AvaliacaoService avaliacaoService;


    public ReservaAlocacaoDTO salvar(ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO) throws RegraDeNegocioException {
        ReservaAlocacaoEntity reservaAlocacaoEntity = converterEntity(reservaAlocacaoCreateDTO);
        alterarData(reservaAlocacaoCreateDTO, reservaAlocacaoEntity);
        ReservaAlocacaoEntity saveAlocacao = reservaAlocacaoRepository.save(reservaAlocacaoEntity);
        saveAlocacao.getAluno().setStatusAluno(reservaAlocacaoCreateDTO.getStatusAluno());
        ReservaAlocacaoDTO reservaAlocacaoDTO = converterEmDTO(saveAlocacao);
        avaliacaoService.cancelarAvaliacao(reservaAlocacaoCreateDTO.getIdAvaliacao());
        vagaService.alterarQuantidadeDeVagas(reservaAlocacaoCreateDTO.getIdVaga());
        return reservaAlocacaoDTO;
    }
    //public ReservaAlocacaoDTO editar()

    private static void alterarData(ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO,
                                    ReservaAlocacaoEntity reservaAlocacaoEntity) {

        if (reservaAlocacaoCreateDTO.getStatusAluno() == StatusAluno.RESERVADO) {
            reservaAlocacaoEntity.setDataReserva(reservaAlocacaoCreateDTO.getData());
        } else if (reservaAlocacaoCreateDTO.getStatusAluno() == StatusAluno.ALOCADO) {
            reservaAlocacaoEntity.setDataAlocacao(reservaAlocacaoCreateDTO.getData());
        } else if (reservaAlocacaoCreateDTO.getStatusAluno() == StatusAluno.DESALOCADO) {
            reservaAlocacaoEntity.setDataCancelamento(reservaAlocacaoCreateDTO.getData());
        }
    }

    public ReservaAlocacaoEntity findById(Integer id) throws RegraDeNegocioException {
        ReservaAlocacaoEntity alocacaoEntity = reservaAlocacaoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Reserva não encontrada!"));
        return alocacaoEntity;
    }

    private ReservaAlocacaoEntity converterEntity(ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO) throws RegraDeNegocioException {
        AlunoEntity alunoEntity = alunoService.findById(reservaAlocacaoCreateDTO.getIdAluno());
        VagaEntity vagaEntity = vagaService.findById(reservaAlocacaoCreateDTO.getIdVaga());
        AvaliacaoEntity avaliacaoEntity = avaliacaoService.findById(reservaAlocacaoCreateDTO.getIdAvaliacao());
        ReservaAlocacaoEntity reservaAlocacaoEntity = new ReservaAlocacaoEntity(null,
                reservaAlocacaoCreateDTO.getDescricao(), null, null, null, null,
                reservaAlocacaoCreateDTO.getStatusAluno(),
                alunoEntity, vagaEntity, avaliacaoEntity);

        return reservaAlocacaoEntity;
    }

    private ReservaAlocacaoDTO converterEmDTO(ReservaAlocacaoEntity reservaAlocacaoEntity) {
        VagaDTO vagaDTO = vagaService.converterEmDTO(reservaAlocacaoEntity.getVaga());
        AvaliacaoDTO avaliacaoDTO = avaliacaoService.converterEmDTO(reservaAlocacaoEntity.getAvaliacao());
        AlunoDTO alunoDTO = alunoService.converterEmDTO(reservaAlocacaoEntity.getAluno());
        ReservaAlocacaoDTO reservaAlocacaoDTO = new ReservaAlocacaoDTO(reservaAlocacaoEntity.getCodigo()
                , vagaDTO,
                alunoDTO,
                avaliacaoDTO,
                reservaAlocacaoEntity.getStatusAluno());
        return reservaAlocacaoDTO;
    }

}
