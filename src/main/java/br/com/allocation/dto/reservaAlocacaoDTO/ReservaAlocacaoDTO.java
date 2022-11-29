package br.com.allocation.dto.reservaAlocacaoDTO;

import br.com.allocation.entity.AlunoEntity;
import br.com.allocation.entity.AvaliacaoEntity;
import br.com.allocation.entity.VagaEntity;
import br.com.allocation.enums.Avaliacao;
import br.com.allocation.enums.Situacao;
import br.com.allocation.enums.StatusAluno;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservaAlocacaoDTO {

    private Integer codigo;
    private VagaEntity vaga;
    private AlunoEntity aluno;
    private Avaliacao avaliacaoEntity;
    private LocalDate dataReserva;
    //private LocalDate dataAlocacao;
    private StatusAluno situacao;
}
