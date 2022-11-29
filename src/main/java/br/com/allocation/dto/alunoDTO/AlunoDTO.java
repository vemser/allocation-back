package br.com.allocation.dto.alunoDTO;

import br.com.allocation.dto.tecnologiaDTO.TecnologiaDTO;
import br.com.allocation.enums.Area;
import br.com.allocation.enums.StatusAluno;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlunoDTO {
    private String nome;
    private String email;
    private Area area;
    private Set<TecnologiaDTO> tecnologias;
    private String programa;
    private String emProcesso;
    private StatusAluno statusAluno;
}
