package br.com.allocation.dto.vagaDTO;

import br.com.allocation.entity.ProgramaEntity;
import br.com.allocation.enums.Situacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VagaDTO {
    private String nome;
    private Integer codigo;
    private Integer quantidade;
    private Situacao situacao;
    private ProgramaEntity programaEntity;
    private String observacoes;
    //private String nivel;
    //private String funcao;
}
