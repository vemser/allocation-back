package br.com.allocation.service.factory;

import br.com.allocation.dto.reservaAlocacaoDTO.ReservaAlocacaoCreateDTO;
import br.com.allocation.entity.ReservaAlocacaoEntity;
import br.com.allocation.enums.StatusAluno;

import java.time.LocalDate;

public class ReservaAlocacaoFactory {
    public static ReservaAlocacaoEntity getReservaAlocacaoEntity() {
        ReservaAlocacaoEntity reservaAlocacaoEntity = new ReservaAlocacaoEntity(1,
                2,
                "xx",
                LocalDate.now().plusDays(2),
                LocalDate.now().plusMonths(1),
                LocalDate.now().plusMonths(1),
                null,
                StatusAluno.RESERVADO,
                AlunoFactory.getAlunoEntity(),
                VagaFactory.getVagaEntity(),
                AvaliacaoFactory.getAvalicaoEntity());
        return reservaAlocacaoEntity;
    }

    public static ReservaAlocacaoCreateDTO getReservaAlocacaoCreateDTO(){
        ReservaAlocacaoCreateDTO reservaAlocacaoCreateDTO = new ReservaAlocacaoCreateDTO();
        reservaAlocacaoCreateDTO.setIdAvaliacao(1);
        reservaAlocacaoCreateDTO.setIdAluno(1);
        reservaAlocacaoCreateDTO.setDataReserva(LocalDate.now().plusDays(2));
        reservaAlocacaoCreateDTO.setDataAlocacao(LocalDate.now());
        reservaAlocacaoCreateDTO.setDescricao("XX");
        reservaAlocacaoCreateDTO.setIdVaga(1);
        reservaAlocacaoCreateDTO.setDataCancelamento(LocalDate.now().plusMonths(1));
        reservaAlocacaoCreateDTO.setDataFinalizado(LocalDate.now().plusYears(1));
        return reservaAlocacaoCreateDTO;
    }
}
