package br.com.allocation.dto.alunoDTO;

import br.com.allocation.enums.Area;
import br.com.allocation.enums.Situacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlunoDTO {
    private Integer idAluno;
    private String nome;
    private String email;
    private Area area;
    private List<String> tecnologias;
    private Integer idPrograma;
    private String emProcesso;
    private Situacao situacao;
}
