package br.com.allocation.dto.alunoDTO;

import br.com.allocation.enums.Area;
import br.com.allocation.enums.SituacaoAllocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlunoDTO extends AlunoCreateDTO {
    private Integer idAluno;
    private String emProcesso;

    public AlunoDTO(String nome, String email, Integer idPrograma, Area area, String cidade, String estado, String telefone, String descricao, SituacaoAllocation situacao, List<String> tecnologias, Integer idAluno, String emProcesso) {
        super(nome, email, idPrograma, area, cidade, estado, telefone, descricao, situacao, tecnologias);
        this.idAluno = idAluno;
        this.emProcesso = emProcesso;
    }
}
