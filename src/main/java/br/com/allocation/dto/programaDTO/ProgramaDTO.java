package br.com.allocation.dto.programaDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgramaDTO {

    private Integer idPrograma;
    private String nome;
    private String descricao;
    private String situacao;
}
