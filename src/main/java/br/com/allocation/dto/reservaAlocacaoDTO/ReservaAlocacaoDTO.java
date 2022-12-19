package br.com.allocation.dto.reservaAlocacaodto;

import br.com.allocation.dto.alunodto.AlunoDTO;
import br.com.allocation.dto.avaliacaodto.AvaliacaoDTO;
import br.com.allocation.dto.vagadto.VagaDTO;
import br.com.allocation.enums.SituacaoAllocation;
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

    private Integer idReservaAlocacao;
    private VagaDTO vaga;
    private AlunoDTO aluno;
    private AvaliacaoDTO avaliacaoEntity;
    private SituacaoAllocation situacaoAllocation;
    private String descricao;
    private LocalDate dataReserva;
    private LocalDate dataAlocacao;
    private LocalDate dataCancelamento;
    private LocalDate dataFinalizado;

}
