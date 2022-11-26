package br.com.allocation.dto.AlunoDTO;

import br.com.allocation.enums.Area;
import br.com.allocation.enums.Situacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlunoDTO {
    private String nome;
    private Area area;
    private String email;
    //private List<Tecnologia> tecnologias;
    //private String emProcesso;
    private Situacao situacao;
}
