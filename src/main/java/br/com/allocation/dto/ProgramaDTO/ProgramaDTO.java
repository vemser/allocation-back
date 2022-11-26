package br.com.allocation.dto.ProgramaDTO;

import br.com.allocation.enums.Situacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgramaDTO {
    private String nome;
    private String descricao;
    private LocalDate dataCriacao;
    private Situacao situacao;
}
