package br.com.allocation.dto.reservaAlocacaoDTO;

import br.com.allocation.dto.alunoDTO.AlunoDTO;
import br.com.allocation.dto.avaliacaoDTO.AvaliacaoDTO;
import br.com.allocation.dto.vagaDTO.VagaDTO;
import br.com.allocation.enums.StatusAluno;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservaAlocacaoDTO {

    private Integer idReservaAlocacao;
    private VagaDTO vaga;
    private AlunoDTO aluno;
    private AvaliacaoDTO avaliacaoEntity;
    private StatusAluno situacao;


}
