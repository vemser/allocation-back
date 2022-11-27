package br.com.allocation.dto.programaDTO;

import br.com.allocation.enums.Situacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgramaDTO {
    private String nome;
    private String descricao;
    private Situacao situacao;
}
