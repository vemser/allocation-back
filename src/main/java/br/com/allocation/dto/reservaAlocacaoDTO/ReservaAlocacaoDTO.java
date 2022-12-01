package br.com.allocation.dto.reservaAlocacaoDTO;

import br.com.allocation.dto.alunoDTO.AlunoDTO;
import br.com.allocation.dto.avaliacaoDTO.AvaliacaoDTO;
import br.com.allocation.dto.vagaDTO.VagaDTO;
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

    private Integer idReservaAlocacao;
    private VagaDTO vaga;
    private AlunoDTO aluno;
    private AvaliacaoDTO avaliacaoEntity;
    private StatusAluno statusAluno;
    private LocalDate dataReserva;
    private LocalDate dataAlocacao;
    private LocalDate dataCancelamento;
    private LocalDate dataFinalizado;

}
