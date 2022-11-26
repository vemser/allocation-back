package br.com.allocation.dto.AlunoDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlunoDTO {
    private String nome;
    private String area;
    private String email;
    private String tecnologia;
    private String emProcesso;
    private String situacao;
}
