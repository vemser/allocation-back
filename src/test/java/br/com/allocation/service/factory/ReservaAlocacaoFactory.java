package br.com.allocation.service.factory;

import br.com.allocation.entity.ReservaAlocacaoEntity;
import br.com.allocation.enums.StatusAluno;

import java.time.LocalDate;

public class ReservaAlocacaoFactory {
    public static ReservaAlocacaoEntity getReservaAlocacaoEntity() {
        ReservaAlocacaoEntity reservaAlocacaoEntity = new ReservaAlocacaoEntity(1,
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
}
