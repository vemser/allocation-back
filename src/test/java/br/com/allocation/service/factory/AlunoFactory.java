package br.com.allocation.service.factory;

import br.com.allocation.dto.alunoDTO.AlunoDTO;
import br.com.allocation.entity.AlunoEntity;
import br.com.allocation.enums.Area;
import br.com.allocation.enums.SituacaoAllocation;

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
                SituacaoAllocation.DISPONIVEL,
                Collections.emptySet(),
                ProgramaFactory.getProgramaEntity(),
                Collections.emptySet(),
                Collections.emptySet());
        return alunoEntity;
    }

    public static AlunoDTO getAlunoDTO() {
        AlunoDTO alunoDTO = new AlunoDTO(1,
                "joao",
                "kaio@bol.com",
                Area.FRONTEND,
                Collections.emptyList(),
                1,
                "nao",
                SituacaoAllocation.DISPONIVEL);
        return alunoDTO;
    }
}
