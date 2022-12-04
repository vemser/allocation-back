package br.com.allocation.service.factory;

import br.com.allocation.dto.alunoDTO.AlunoDTO;
import br.com.allocation.entity.AlunoEntity;
import br.com.allocation.enums.Area;
import br.com.allocation.enums.Situacao;

import java.util.Collections;

public class AlunoFactory {
    public static AlunoEntity getAlunoEntity() {
        AlunoEntity alunoEntity = new AlunoEntity(19,
                "Gustavo Lucena",
                "jhennyfer.sobrinho@dbccompany.com.br",
                Area.FRONTEND,
                "Pato Branco",
                "Paraná",
                "99595-1313",
                "xxx",
                Situacao.DISPONIVEL,
                Collections.emptySet(),
                ProgramaFactory.getProgramaEntity(),
                Collections.emptySet(),
                Collections.emptySet());
        return alunoEntity;
    }

    public static AlunoDTO getAlunoDTO() {
        AlunoDTO alunoDTO = new AlunoDTO();
        alunoDTO.setIdAluno(1);
        alunoDTO.setNome("joao");
        alunoDTO.setEmail("kaio@bol.com");
        alunoDTO.setArea(Area.FRONTEND);
        alunoDTO.setTecnologias(Collections.emptyList());
        alunoDTO.setIdPrograma(1);
        alunoDTO.setEmProcesso("nao");
        alunoDTO.setSituacao(Situacao.DISPONIVEL);

        return alunoDTO;
    }
}
