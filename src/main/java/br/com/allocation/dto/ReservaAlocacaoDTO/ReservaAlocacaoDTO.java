package br.com.allocation.dto.ReservaAlocacaoDTO;

import br.com.allocation.entity.AvaliacaoEntity;
import br.com.allocation.enums.Situacao;
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
    private String vaga;
    private String aluno;
    private AvaliacaoEntity avaliacaoEntity;
    private LocalDate dataReserva;
    private LocalDate dataAlocacao;
    private Situacao situacao;
}
